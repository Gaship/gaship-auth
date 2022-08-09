package shop.gaship.gashipauth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * secure key와 관련된 설정을 위한 class.
 *
 * @author : 조재철
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "secure.keymanager")
public class AuthenticationConfig {

    private String url;

    private String appkey;

    private String jwtSecureKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getJwtSecureKey() {
        return jwtSecureKey;
    }

    public void setJwtSecureKey(String jwtSecureKey) {
        this.jwtSecureKey = jwtSecureKey;
    }

    /**
     * secure key를 얻어온 후 Base64로 인코딩 한 것, HS256 알고리즘을 가지는
     * SecretKeySpec 객체를 반환하는 빈을 등록하는 메서드.
     *
     * @param secureManagerConfig findSecretDataFromSecureKeyManager를 통해서
     *                            NHN Secure key manager의 질의 기능을 사용하기 위한 매계변수입니다.
     * @return secret key 반환.
     */
    @Bean
    public Key tokenKey(SecureManagerConfig secureManagerConfig) {
        String secretJwtKey = secureManagerConfig.findSecretDataFromSecureKeyManager(jwtSecureKey);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretJwtKey);

        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }
}
