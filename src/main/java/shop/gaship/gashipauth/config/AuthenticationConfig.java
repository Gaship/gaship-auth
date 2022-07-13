package shop.gaship.gashipauth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import shop.gaship.gashipauth.util.WebClientUtil;
import shop.gaship.gashipauth.util.dto.SecureKeyResponse;

@Configuration
public class AuthenticationConfig {
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


    @Bean
    public Key tokenKey() {
        SecureKeyResponse secureKey = new WebClientUtil<SecureKeyResponse>()
            .get(
                baseUrl,
                "/keymanager/v1.0/appkey/" + appKey + "/secrets/" + jwtKeypair,
                null,
                null,
                SecureKeyResponse.class
            ).getBody();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes =
            DatatypeConverter.parseBase64Binary(secureKey.getBody().getSecret());

        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    @Bean
    public String mailSenderSecretKey() {
        return new WebClientUtil<SecureKeyResponse>()
            .get(
                baseUrl,
                "/keymanager/v1.0/appkey/" + appKey + "/secrets/" + mailKeypair,
                null,
                null,
                SecureKeyResponse.class
            ).getBody()
            .getBody()
            .getSecret();
    }

    @Bean
    public String mailAppKey() {
        return mailAppKey;
    }
}
