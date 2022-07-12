package shop.gaship.gashipauth.verify.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.verify.dto.EmailReceiver;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.util.EmailSenderUtil;

/**
 * packageName    : shop.gaship.gashipauth.verify.service <br/>
 * fileName       : VerifyService <br/>
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
public class VerifyService {
    private static final String TEMPLATE_ID  = "signUpTemplate";

    private final EmailSenderUtil emailSenderUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public boolean sendSignUpVerifyEmail(String receiverEmail) {
        String verifyCode = UUID.randomUUID().toString();
        redisTemplate.opsForSet().add("abc", String.valueOf(true));

        // 해당 url은 프론트 서버를 의미한다.
        Map<String, String> templateParam =
            Map.of("link", "http://localhost:8080/signUp/email-verify/" + verifyCode);
        String receiveType = "MRT0";

        EmailSendDto emailSendDto = EmailSendDto.builder()
            .templateId(TEMPLATE_ID)
            .templateParameter(templateParam)
            .receiverList(List.of(new EmailReceiver(receiverEmail,"" ,receiveType)))
            .build();
        emailSenderUtil.sendMail(emailSendDto);

        return true;
    }
}