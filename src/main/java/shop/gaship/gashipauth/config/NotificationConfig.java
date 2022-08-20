package shop.gaship.gashipauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "notification.mail")
public class NotificationConfig {

    private String url;
    private String appkey;


    public String getUrl() {
        return url;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
