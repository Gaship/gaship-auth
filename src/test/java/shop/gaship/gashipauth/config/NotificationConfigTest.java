package shop.gaship.gashipauth.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith({SpringExtension.class})
@EnableConfigurationProperties(value = {NotificationConfig.class})
@TestPropertySource("classpath:application.properties")
class NotificationConfigTest {

    @Autowired
    NotificationConfig config;

    @Test
    void secureManagerConfigTest() {
        assertThat(config.getUrl()).isEqualTo("https://api-mail.cloud.toast.com");
        assertThat(config.getAppkey()).isEqualTo("4M06c6oyOFwfdra7");

    }
}
