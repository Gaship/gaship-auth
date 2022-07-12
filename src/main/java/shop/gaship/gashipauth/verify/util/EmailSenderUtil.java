package shop.gaship.gashipauth.verify.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.util.WebClientUtil;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.dto.EmailSendSuccessfulDto;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;

/**
 * packageName    : shop.gaship.gashipauth.verify.util <br/>
 * fileName       : EmailSenderUtil <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Component
//@RequiredArgsConstructor
public class EmailSenderUtil {
    private static final String MAIL_SENDER_BASE_URL = "https://api-mail.cloud.toast.com";

    private final String mailSenderSecretKey;
    private final String mailAppKey;

    public EmailSenderUtil(String mailSenderSecretKey, String mailAppKey) {
        this.mailSenderSecretKey = mailSenderSecretKey;
        this.mailAppKey = mailAppKey;
    }

    public void sendMail(EmailSendDto emailSendDto) {
        Map<String, List<String>> headers = new HashMap<>();
        List<String> headerValue = List.of(mailSenderSecretKey);
        headers.put("X-Secret-Key", headerValue);

        ResponseEntity<EmailSendSuccessfulDto> result =
            new WebClientUtil<EmailSendSuccessfulDto>().post(
                MAIL_SENDER_BASE_URL,
                "/email/v2.0/appKeys/" + mailAppKey + "/sender/mail",
                null,
                headers,
                emailSendDto,
                EmailSendSuccessfulDto.class
            );
        EmailSendSuccessfulDto.Header header = Objects.requireNonNull(result.getBody()).getHeader();

        if (Boolean.FALSE.equals(header.getIsSuccessful())) {
            throw new EmailSendFailureException(header.getResultMessage());
        }
    }
}
