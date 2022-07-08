package shop.gaship.gashipauth.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import shop.gaship.gashipauth.exceptions.NoResponseDataException;

/**
 * packageName    : shop.gaship.gashipauth.util
 * fileName       : WebClientUtil
 * author         : 김민수
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        oct_sky_out       최초 생성
 */
@Slf4j
public class WebClientUtil<T> {
    private static final String errorMessage = "응답결과가 존재하지 않습니다.";
    private static final Duration timeOut = Duration.of(3, ChronoUnit.SECONDS);

    public T get(String baseUrl, String urn,
                 @Nullable List<QueryParam> queryParams,
                 @Nullable Map<String, List<String>> headers,
                 Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).get()
            , urn, queryParams, headers, responseEntity);
    }

    public <U> T post(String baseUrl, String urn,
                      @Nullable List<QueryParam> queryParams,
                      @Nullable Map<String, List<String>> headers,
                      U bodyValue,
                      Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).post()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }

    public <U> T put(String baseUrl, String urn,
                     @Nullable List<QueryParam> queryParams,
                     @Nullable Map<String, List<String>> headers,
                     U bodyValue,
                     Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).put()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }


    public <U> T patch(String baseUrl, String urn,
                       @Nullable List<QueryParam> queryParams,
                       @Nullable Map<String, List<String>> headers,
                       U bodyValue,
                       Class<T> responseEntity) {
        return createHasBodyMethod(() -> WebClient.create(baseUrl).patch()
            , urn, queryParams, headers, bodyValue, responseEntity);
    }

    public T delete(String baseUrl, String urn,
                    @Nullable List<QueryParam> queryParams,
                    @Nullable Map<String, List<String>> headers,
                    Class<T> responseEntity) {
        return createMethod(() -> WebClient.create(baseUrl).delete()
            , urn, queryParams, headers, responseEntity);
    }

    private <U> T createMethod(Supplier<WebClient.RequestHeadersUriSpec<?>> restMethod, String urn,
                               List<QueryParam> queryParams,
                               Map<String, List<String>> headers, Class<T> responseEntity) {
        SuccessResult<T> successResult = new SuccessResult<>();
        restMethod.get()
            .uri(uriBuilder -> setQueryParams(uriBuilder.path(urn), queryParams).build())
            .headers(httpHeaders -> setHeaders(httpHeaders, headers))
            .retrieve()
            .toEntity(responseEntity)
            .timeout(timeOut)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(errorMessage));

        log.debug("{}", successResult.getResult());
        return successResult.getResult();
    }

    private <U> T createHasBodyMethod(Supplier<WebClient.RequestBodyUriSpec> restMethod, String urn,
                                      List<QueryParam> queryParams,
                                      Map<String, List<String>> headers, U bodyValue,
                                      Class<T> responseEntity) {
        SuccessResult<T> successResult = new SuccessResult<>();
        restMethod.get()
            .uri(uriBuilder -> setQueryParams(uriBuilder.path(urn), queryParams).build())
            .headers(httpHeaders -> setHeaders(httpHeaders, headers))
            .bodyValue(bodyValue)
            .retrieve()
            .toEntity(responseEntity)
            .timeout(timeOut)
            .blockOptional()
            .orElseThrow(() -> new NoResponseDataException(errorMessage));


        log.debug("{}", successResult.getResult());
        return successResult.getResult();
    }

    private UriBuilder setQueryParams(UriBuilder builder, List<QueryParam> queryParams) {
        if (Objects.nonNull(queryParams)) {
            log.debug("queryParams len : ", queryParams.size());
            queryParams.forEach(queryParam ->
                builder.queryParam(queryParam.getName(), queryParam.getValues()));
        }

        return builder;
    }

    private void setHeaders(HttpHeaders httpHeaders, Map<String, List<String>> headers) {
        if (Objects.nonNull(headers)) {
            httpHeaders.putAll(headers);
        }
    }
}
