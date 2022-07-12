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
import org.springframework.http.ResponseEntity;

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
        ResponseEntity<JsonPlaceHolderResult> result =
            new WebClientUtil<JsonPlaceHolderResult>().get(
            "https://jsonplaceholder.typicode.com",
            "/todos/1",
            null,
            null,
            JsonPlaceHolderResult.class
        );

        JsonPlaceHolderResult bodyResult = result.getBody();

        assertThat(bodyResult.getCompleted()).isFalse();
        assertThat(bodyResult.getId()).isEqualTo(1);
        assertThat(bodyResult.getUserId()).isEqualTo(1);
        assertThat(bodyResult.getTitle()).isNotNull();
    }

    @Test
    void getTestQueryParamAndHeaders() {
        List<QueryParam> params = new ArrayList<>();
        params.add(new QueryParam("test", "test"));
        params.add(new QueryParam("test2", "test"));

        Map<String, List<String>> headers = new HashMap<>();
        headers.put(HttpHeaders.CONTENT_TYPE,
            Collections.singletonList(MediaType.APPLICATION_JSON_VALUE));

        ResponseEntity<JsonPlaceHolderResult> result =
            new WebClientUtil<JsonPlaceHolderResult>().get(
            "https://jsonplaceholder.typicode.com",
            "/todos/1",
            params, headers,
            JsonPlaceHolderResult.class
        );

        JsonPlaceHolderResult bodyResult = result.getBody();

        assertThat(bodyResult.getCompleted()).isFalse();
        assertThat(bodyResult.getId()).isEqualTo(1);
        assertThat(bodyResult.getUserId()).isEqualTo(1);
        assertThat(bodyResult.getTitle()).isNotNull();
    }

    @Test
    void postTest() {
        Map<String, String> value = new HashMap<>();
        value.put("hello", "hi");
        JsonPlaceHolderResult result = new WebClientUtil<JsonPlaceHolderResult>().post(
                "https://jsonplaceholder.typicode.com",
                "/posts",
                null,
                null,
                value,
                JsonPlaceHolderResult.class
            ).getBody();

        assertThat(result.getId()).isEqualTo(101);
    }
}
