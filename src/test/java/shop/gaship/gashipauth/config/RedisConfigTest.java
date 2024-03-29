package shop.gaship.gashipauth.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith({SpringExtension.class})
@TestPropertySource("classpath:application.properties")
@EnableConfigurationProperties(value = {SecureManagerConfig.class})
@Import({RedisConfig.class})
class RedisConfigTest {

    @Autowired
    RedisConfig config;

    @Test
    void redisConfigTest() {
        assertThat(config.getHost()).isEqualTo("a93c53427dd84868bdc3402d94270bf4");
        assertThat(config.getPort()).isEqualTo(6379);
        assertThat(config.getPassword()).isEqualTo("c61df19084544df5a11f5c9a57abb6c2");
        assertThat(config.getDatabase()).isEqualTo(27);
    }
}
