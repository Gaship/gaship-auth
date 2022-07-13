package shop.gaship.gashipauth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RedisTest {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void insertGet_string() {
        redisTemplate.opsForHash().put("1", "1-1", "hi");
        String result = redisTemplate.opsForHash().get("1", "1-1")
            .toString();
        assertThat(result).isEqualTo("hi");
        redisTemplate.delete("1");
    }

    @Test
    void setTest() {
        redisTemplate.opsForSet().add("exam", "hi");
        String result = redisTemplate.opsForSet().pop("exam");
        String result2 = redisTemplate.opsForSet().pop("exam");
        assertThat(result).isEqualTo("hi");
        assertThat(result2).isEqualTo(null);
    }
}
