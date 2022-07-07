package shop.gaship.gashipauth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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
    }
}