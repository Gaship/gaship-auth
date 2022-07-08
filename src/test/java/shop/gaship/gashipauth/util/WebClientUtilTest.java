package shop.gaship.gashipauth.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * packageName    : shop.gaship.gashipauth.util
 * fileName       : WebClientUtilTest
 * author         : oct_sky_out
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        oct_sky_out       최초 생성
 */
@Slf4j
class WebClientUtilTest {
    @Test
    void get() {
        JsonPlaceHolderResult result = new WebClientUtil<JsonPlaceHolderResult>().get(
            "https://jsonplaceholder.typicode.com",
            "/todos/1",
            null,
            null,
            JsonPlaceHolderResult.class
        );

        assertThat(result.getCompleted()).isFalse();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getTitle()).isNotNull();
    }

    @Test
    void getTestQueryParamAndHeaders() {
        List<QueryParam> params = new ArrayList<>();
        params.add(new QueryParam("test", "test"));
        params.add(new QueryParam("test2", "test"));

        Map<String, List<String>> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE,
            Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        JsonPlaceHolderResult result = new WebClientUtil<JsonPlaceHolderResult>().get(
            "https://jsonplaceholder.typicode.com",
            "/todos/1",
            params, headers,
            JsonPlaceHolderResult.class
        );

        assertThat(result.getCompleted()).isFalse();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getTitle()).isNotNull();
    }

    @Test
    void postTest() throws InterruptedException {
        Map<String, String> value = new HashMap<>();
        value.put("hello", "hi");
        new WebClientUtil<JsonPlaceHolderResult>().post(
                "https://jsonplaceholder.typicode.com",
                "/posts",
                null,
                null,
                value,
                JsonPlaceHolderResult.class
            );
    }
}
