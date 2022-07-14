package shop.gaship.gashipauth.verify.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.verify.dto.EmailReceiver;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;
import shop.gaship.gashipauth.verify.service.VerifyService;
import shop.gaship.gashipauth.verify.util.EmailSenderUtil;

/**
 * packageName    : shop.gaship.gashipauth.verify.service <br/>
 * fileName       : VerifyServiceImpl <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {
    private static final String TEMPLATE_ID = "signUpTemplate";

    private final String gashipFrontServerUrl;
    private final EmailSenderUtil emailSenderUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public boolean sendSignUpVerifyEmail(String receiverEmail) {
        String verifyCode = UUID.randomUUID().toString();
        redisTemplate.opsForSet().add(verifyCode, String.valueOf(true));
        redisTemplate.expire(verifyCode, 3, TimeUnit.MINUTES); // 3분제

        // 해당 url은 프론트 서버를 의미한다.
        Map<String, String> templateParam =
            Map.of("link", gashipFrontServerUrl + "/members/signUp/email-verify/" + verifyCode);
        String receiveType = "MRT0";

        EmailSendDto emailSendDto = EmailSendDto.builder()
            .templateId(TEMPLATE_ID)
            .templateParameter(templateParam)
            .receiverList(List.of(new EmailReceiver(receiverEmail, "", receiveType)))
            .build();
        emailSenderUtil.sendMail(emailSendDto);

        return true;
    }

    public boolean approveVerificationEmail(String verifyCode) {
        String result = redisTemplate.opsForSet().pop(verifyCode);
        
        if (Objects.isNull(result)) {
            throw new EmailVerificationImpossibleException();
        }
        
        return Boolean.parseBoolean(result);
    }
}
