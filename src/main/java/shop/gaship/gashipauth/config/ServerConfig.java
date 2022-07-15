package shop.gaship.gashipauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 서버의 일반적인 환경설정을 수행하는 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
public class ServerConfig {
    @Value("${gaship.front-server}")
    private String gashipFrontServerUrl;

    @Bean
    public String gashipFrontServerUrl(){
        return this.gashipFrontServerUrl;
    }
}
