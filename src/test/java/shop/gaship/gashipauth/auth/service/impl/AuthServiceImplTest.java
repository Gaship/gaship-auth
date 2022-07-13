package shop.gaship.gashipauth.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.auth.dto.Response;
import shop.gaship.gashipauth.auth.dto.Response.Body;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 * packageName    : shop.gaship.gashipauth.auth.service.impl fileName       : AuthServiceImplTest
 * author         : jo date           : 2022/07/12 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2022/07/12        jo       최초 생성
 */
@Import(AuthServiceImpl.class)
@ExtendWith(SpringExtension.class)
class AuthServiceImplTest {

    @Autowired
    private AuthService service;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    Response response;

    @MockBean
    RedisTemplate redisTemplate;

    @Test
    void logout() {
        // given
        Body body = Body.builder()
            .state(HttpStatus.BAD_REQUEST.value())
            .data(Collections.emptyList())
            .result("fail")
            .massage("잘못된 요청입니다.")
            .error(Collections.emptyList())
            .build();

        ResponseEntity<?> ok = ResponseEntity.ok(body);

        given(jwtTokenUtil.validateToken(any(String.class))).willReturn(false);
        //given(response.fail(any(String.class), any(HttpStatus.class))).willReturn(responseEntity);
        // TODO : 이것 공부해보자
        doReturn(ok).when(response).fail(any(),any());
        // when
        ResponseEntity<?> responseEntity = service.logout("abc");

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(ok.getStatusCode());
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(ok.getStatusCodeValue());
        assertThat(responseEntity.getBody()).isEqualTo(ok.getBody());
    }
}