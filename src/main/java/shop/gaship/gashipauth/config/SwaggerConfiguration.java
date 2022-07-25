package shop.gaship.gashipauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 관련 설정에 대한 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Swagger 설정의 핵심으로 문서화 객체를 빈으로 등록하는 메서드
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("shop.gaship.gashipauth"))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * Swagger API 문서에 대한 설명을 표기하는 메소드
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Spring Boot Open Api Test with Swagger11111111111")
            .description("설명 부분")
            .version("1.0.0")
            .build();
    }
}