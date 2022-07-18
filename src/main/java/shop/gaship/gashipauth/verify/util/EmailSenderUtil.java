package shop.gaship.gashipauth.verify.util;

import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipauth.config.NotificationConfig;
import shop.gaship.gashipauth.config.SecureManagerConfig;
import shop.gaship.gashipauth.exceptions.NoResponseDataException;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.dto.EmailSendSuccessfulDto;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;

/**
 * 이메일을 전송하기위한 유틸형 클래스입니다
 *
 * @author : 김민수
 * @since 1.0
 */
@Component
public class EmailSenderUtil {
    private static final String ERROR_MESSAGE = "요청 수행에 실패했습니다.";
    private final String mailBaseurl;
    private final String mailSenderSecretKey;
    private final String mailAppKey;

    public EmailSenderUtil(SecureManagerConfig secureManagerConfig,
                           NotificationConfig notificationConfig) {
        this.mailBaseurl = notificationConfig.getUrl();
        this.mailAppKey = notificationConfig.getAppkey();
        this.mailSenderSecretKey = secureManagerConfig.mailSenderSecretKey();
    }

    /**
     * 이메일을 전송합니다.
     *
     * @param emailSendDto 이메일을 전송하기위한 정보들이 담긴 객체입니다.
     */
    public void sendMail(EmailSendDto emailSendDto) {
        ResponseEntity<EmailSendSuccessfulDto> result =
            WebClient.create(mailBaseurl).post()
                .uri("/email/v2.0/appKeys/{mailAppKey}/sender/mail", mailAppKey)
                .headers(
                    httpHeaders -> httpHeaders.put("X-Secret-Key", List.of(mailSenderSecretKey)))
                .bodyValue(emailSendDto)
                .retrieve()
                .toEntity(EmailSendSuccessfulDto.class)
                .blockOptional()
                .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE));

        EmailSendSuccessfulDto.Header header = Objects.requireNonNull(result.getBody()).getHeader();

        if (Boolean.FALSE.equals(header.getIsSuccessful())) {
            throw new EmailSendFailureException(header.getResultMessage());
        }
    }
}
