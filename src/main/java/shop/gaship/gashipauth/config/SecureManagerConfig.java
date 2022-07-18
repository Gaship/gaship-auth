package shop.gaship.gashipauth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.Duration;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
@ConfigurationProperties(prefix = "secure-key-manager")
public class SecureManagerConfig {
    private static final String ERROR_MESSAGE = "응답결과가 존재하지 않습니다.";

    private String url;
    private String appKey;
    private String jwtSecureKey;
    private String emailNotificationKey;

    public Key tokenKey() {
        String secureKey = findSecretDataFromSecureKeyManager(jwtSecureKey);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes =
            DatatypeConverter.parseBase64Binary(secureKey);

        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    public String mailSenderSecretKey() {
        return findSecretDataFromSecureKeyManager(emailNotificationKey);
    }

    private String findSecretDataFromSecureKeyManager(String keyId){
        return Objects.requireNonNull(WebClient.create(url).get()
                .uri("/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}", appKey, keyId)
                .retrieve()
                .toEntity(SecureKeyResponse.class)
                .timeout(Duration.ofSeconds(5))
                .blockOptional()
                .orElseThrow(() -> new NoResponseDataException(ERROR_MESSAGE))
                .getBody())
            .getBody()
            .getSecret();
    }

    public String getUrl() {
        return url;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getJwtSecureKey() {
        return jwtSecureKey;
    }

    public String getEmailNotificationKey() {
        return emailNotificationKey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setJwtSecureKey(String jwtSecureKey) {
        this.jwtSecureKey = jwtSecureKey;
    }

    public void setEmailNotificationKey(String emailNotificationKey) {
        this.emailNotificationKey = emailNotificationKey;
    }
}
