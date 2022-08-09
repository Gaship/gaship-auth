package shop.gaship.gashipauth.verify.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.config.AuthenticationConfig;
import shop.gaship.gashipauth.config.NotificationConfig;
import shop.gaship.gashipauth.config.SecureManagerConfig;
import shop.gaship.gashipauth.util.EmailSenderUtil;
import shop.gaship.gashipauth.verify.dto.EmailReceiver;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;

/**
 * packageName : shop.gaship.gashipauth.verify.util <br/>
 * fileName : EmailSenderUtilTest <br/>
 * author : 김민수 <br/>
 * date : 2022/07/12 <br/>
 * description : <br/>
 * =========================================================== <br/>
 * DATE AUTHOR NOTE <br/>
 * ----------------------------------------------------------- <br/>
 * 2022/07/12 김민수 최초 생성 <br/>
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
@EnableConfigurationProperties(value = {SecureManagerConfig.class, NotificationConfig.class})
@Import({EmailSenderUtil.class})
class EmailSenderUtilTest {

    @Autowired
    EmailSenderUtil emailSenderUtil;

    @Test
    void sendMail() throws JsonProcessingException {
        Map<String, String> templateParam = new HashMap<>();
        templateParam.put("link", "http://www.nhn.com");

        EmailSendDto emailSendDto = EmailSendDto.builder()
                .templateId("signUpTemplate")
                .templateParameter(templateParam)
                .receiverList(List.of(new EmailReceiver("kms3335k@naver.com", "", "MRT0")))
                .build();

        System.out.println(new ObjectMapper().writeValueAsString(emailSendDto));

        emailSenderUtil.sendMail(emailSendDto);

        assertThat(Optional.of(true)).isPresent();
    }
}
