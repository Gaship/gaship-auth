package shop.gaship.gashipauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : shop.gaship.gashipauth.config <br/>
 * fileName       : ServerConfig <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/15 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/15           김민수               최초 생성                         <br/>
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
