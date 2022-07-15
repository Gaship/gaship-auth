package shop.gaship.gashipauth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Duration;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipauth.exceptions.NoResponseDataException;
import shop.gaship.gashipauth.util.dto.SecureKeyResponse;

/**
 *
 * 보안과 관련된 환경설정을 위한 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
public class AuthenticationConfig {
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";

    @Value("${secure.keymanager.url}")
    private String baseUrl;

    @Value("${secure.keymanager.appkey}")
    private String appKey;

    @Value("${secure.keymanager.jwt-secure-key}")
    private String jwtKeypair;

    @Value("${secure.mail.appkey}")
    private String mailAppKey;

    @Value("${secure.mail.email-notification-secure-key}")
    private String mailKeypair;

    @Value("${secure.mail.url}")
    private String mailBaseurl;

    @Bean
    public Key tokenKey() {
        SecureKeyResponse secureKey = WebClient.create(baseUrl).get()
            .uri("/keymanager/v1.0/appkey/{appKey}/secrets/{jwtKeypair}", appKey, jwtKeypair)
            .retrieve()
            .toEntity(SecureKeyResponse.class)
            .timeout(Duration.ofSeconds(3))
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE))
            .getBody();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes =
            DatatypeConverter.parseBase64Binary(secureKey.getBody().getSecret());

        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    @Bean
    public String mailSenderSecretKey() {
        return WebClient.create(baseUrl).get()
            .uri("/keymanager/v1.0/appkey/{appKey}/secrets/{mailKeypair}", appKey, mailKeypair)
            .retrieve()
            .toEntity(SecureKeyResponse.class)
            .timeout(Duration.ofSeconds(3))
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE))
            .getBody()
            .getBody()
            .getSecret();
    }

    @Bean
    public String mailAppKey() {
        return mailAppKey;
    }

    @Bean
    public String mailBaseurl(){
        return mailBaseurl;
    }
}
