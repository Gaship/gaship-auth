package shop.gaship.gashipauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 서버의 일반적인 환경설정을 수행하는 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "gaship-server")
public class ServerConfig {

    private String frontUrl;
    private String shoppingMallUrl;
    private String paymentsUrl;
    private String schedulerUrl;

    public String getFrontUrl() {
        return frontUrl;
    }

    public String getShoppingMallUrl() {
        return shoppingMallUrl;
    }

    public String getPaymentsUrl() {
        return paymentsUrl;
    }

    public String getSchedulerUrl() {
        return schedulerUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public void setShoppingMallUrl(String shoppingMallUrl) {
        this.shoppingMallUrl = shoppingMallUrl;
    }

    public void setPaymentsUrl(String paymentsUrl) {
        this.paymentsUrl = paymentsUrl;
    }

    public void setSchedulerUrl(String schedulerUrl) {
        this.schedulerUrl = schedulerUrl;
    }
}
