package shop.gaship.gashipauth.verify.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.config.ServerConfig;
import shop.gaship.gashipauth.util.EmailSenderUtil;
import shop.gaship.gashipauth.verify.dto.EmailReceiver;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.dto.VerificationCodeDto;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;
import shop.gaship.gashipauth.verify.service.VerifyService;

/**
 * 검증을 위한 VerifyService의 구현 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    private static final String TEMPLATE_ID = "signUpTemplate";
    private final ServerConfig serverConfig;
    private final EmailSenderUtil emailSenderUtil;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public VerificationCodeDto sendSignUpVerifyEmail(String receiverEmail) {
        String verifyCode = UUID.randomUUID().toString();
        redisTemplate.opsForSet().add(verifyCode, String.valueOf(true));
        redisTemplate.expire(verifyCode, 3, TimeUnit.MINUTES); // 3분제

        // 해당 url은 프론트 서버를 의미한다.
        Map<String, String> templateParam =
                Map.of("link",
                    serverConfig.getClientEndpoint() + "/members/signUp/email-verify/" + verifyCode);
        String receiveType = "MRT0";

        EmailSendDto emailSendDto = EmailSendDto.builder()
                                                .templateId(TEMPLATE_ID)
                                                .templateParameter(templateParam)
                                                .receiverList(
                                                    List.of(new EmailReceiver(receiverEmail,
                                                        "", receiveType)))
                                                .build();
        emailSenderUtil.sendMail(emailSendDto);

        return new VerificationCodeDto(verifyCode);
    }

    @Override
    public boolean approveVerificationEmail(String verifyCode) {
        String result = redisTemplate.opsForSet().pop(verifyCode);
        redisTemplate.opsForSet().add(verifyCode, String.valueOf(false));
        redisTemplate.expire(verifyCode, 3, TimeUnit.MINUTES);

        return true;
    }

    private boolean isCodePopped(String result) {
        if (Objects.isNull(result)) {
            throw new EmailVerificationImpossibleException();
        }

        return !Boolean.parseBoolean(result);
    }

    @Override
    public boolean checkVerificationCode(String verifyCode) {
        if (Objects.nonNull(verifyCode)) {
            Long verifyCodeCount = redisTemplate.opsForSet().size(verifyCode);
            return Objects.requireNonNull(verifyCodeCount) != 0;
        }
        return false;
    }
}
