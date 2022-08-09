package shop.gaship.gashipauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis와 관련된 설정을 위한 class 입니다.
 *
 * @author 조재철
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    private String host;

    private int port;

    private String password;

    private int database;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public int getDatabase() {
        return database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    /**
     * redis 연동을 위한 연결 설정을 한 Lettuce를 반환하는 빈등록 하는 메서드. (Redis Client로 Lettuce를 사용)
     *
     * @param secureManagerConfig findSecretDataFromSecureKeyManager를 통해서
    *                            NHN Secure key manager의 질의 기능을 사용하기 위한 매계변수입니다.
     * @return Redis의 연결 정보를 가진 객체를 반환합니다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(SecureManagerConfig secureManagerConfig) {
        String secretHost = secureManagerConfig.findSecretDataFromSecureKeyManager(host);
        String secretPassword = secureManagerConfig.findSecretDataFromSecureKeyManager(password);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(secretHost);
        configuration.setPort(port);
        configuration.setPassword(secretPassword);
        configuration.setDatabase(database);

        return new LettuceConnectionFactory(configuration);
    }

    /**
     * RedisTemplate 관련 설정을 하여 빈 등록하는 메서드이다.
     *
     * @return 사용자 정의로 설정된 RedisTemplate를 반환합니다.
     */
    @SuppressWarnings("java:S1452") // Redis 키와 value의값으로 객체를 사용하기 위함.
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(null));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}
