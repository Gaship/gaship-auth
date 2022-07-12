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

@Configuration
@PropertySource("classpath:application.properties")
public class JwtConfig {
    @Value("${secure.keymanager.url}")
    private String baseUrl;

    @Value("${secure.keymanager.appkey}")
    private String appKey;

    @Value("${secure.keymanager.jwt-secure-key}")
    private String jwtKeypair;


    @Bean
    public Key tokenKey() {
        String secureKey = new WebClientUtil<String>()
            .get(
                baseUrl,
                "/keymanager/v1.0/appkey/" + appKey + "/secrets/" + jwtKeypair,
                null,
                null,
                String.class
            ).getBody();

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secureKey);

        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }
}
