package shop.gaship.gashipauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * 메인 클래스 테스트.
 *
 * @author : 조재철
 * @since 1.0
 */
@SpringBootTest
class GashipAuthApplicationTest {

    @Test
    void contextLoads(ApplicationContext context) {
        assertThat(context).isNotNull();
    }

}