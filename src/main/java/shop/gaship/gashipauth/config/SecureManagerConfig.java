package shop.gaship.gashipauth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import shop.gaship.gashipauth.exceptions.NoResponseDataException;
import shop.gaship.gashipauth.util.dto.SecureKeyResponse;

/**
 * 보안과 관련된 환경설정을 위한 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "secure-key-manager")
public class SecureManagerConfig {

    private String url;
    private String appKey;
    private String jwtSecureKey;
    private String emailNotificationKey;

    private String localKey;

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

    String findSecretDataFromSecureKeyManager(String keyId) {
        String errorMessage = "응답 결과가 없습니다.";
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(
                new ClassPathResource("github-action.p12").getInputStream(),
                localKey.toCharArray());

            SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
            sslContextBuilder.setProtocol("TLS");
            sslContextBuilder.loadKeyMaterial(clientStore, localKey.toCharArray());
            sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());

            SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslContextBuilder.build());
            CloseableHttpClient httpClient = HttpClients.custom()
                                                        .setSSLSocketFactory(sslConnectionSocketFactory)
                                                        .build();
            HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

            return Objects.requireNonNull(new RestTemplate(requestFactory)
                              .getForEntity(url + "/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}",
                                  SecureKeyResponse.class,
                                  appKey,
                                  keyId)
                              .getBody())
                          .getBody()
                          .getSecret();
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException
            | UnrecoverableKeyException | IOException | KeyManagementException e) {
            throw new NoResponseDataException(errorMessage);
        }
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

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }
}
