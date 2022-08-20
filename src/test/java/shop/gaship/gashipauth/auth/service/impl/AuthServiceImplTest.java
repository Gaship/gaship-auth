package shop.gaship.gashipauth.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.auth.dto.request.ReissueJwtRequestDto;
import shop.gaship.gashipauth.auth.exception.NotFoundRefreshTokenException;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.dto.response.JwtResponseDto;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 * Auth관련 서비스 테스트 입니다.
 *
 * @author : 조재철
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(AuthServiceImpl.class)
class AuthServiceImplTest {

    @Autowired
    AuthService authService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    RedisTemplate redisTemplate;

    @MockBean
    ValueOperations valueOperations;

    @Test
    void logout() {
        // given
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        Integer memberNo = 1;

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForValue().get("RT " + memberNo)).thenReturn(refreshToken);
        when(redisTemplate.delete("RT " + memberNo)).thenReturn(Boolean.TRUE);

        // when
        authService.logout(accessToken, refreshToken, memberNo);

        // then
        verify(redisTemplate).delete("RT " + memberNo);
        verify(redisTemplate.opsForValue())
            .set(accessToken, "logout", JwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC,
                TimeUnit.MILLISECONDS);
    }

    @Test
    void logoutNotFoundRefreshTokenTest() {
        // given
        String accessToken = "access-token";
        String refreshToken = "refresh-token";
        Integer memberNo = 1;

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.delete("RT " + memberNo)).thenReturn(Boolean.TRUE);

        // when, then
        assertThatThrownBy(() -> authService.logout(accessToken, refreshToken, memberNo)).isInstanceOf(
            NotFoundRefreshTokenException.class).hasMessageContaining("해당 refresh token 이 존재하지 않습니다.");

    }

    @Test
    void issueJwt() {
        // given
        UserInfoForJwtRequestDto userInfoForJwtRequestDto = new UserInfoForJwtRequestDto();
        userInfoForJwtRequestDto.setMemberNo(1);
        userInfoForJwtRequestDto.setAuthorities(List.of("ROLE_ADMIN"));
        userInfoForJwtRequestDto.setEmail("rsdlfj@naver.com");

        String accessToken = "access-token";
        String refreshToken = "refresh-token";

        Date accessTokenExpireDate = new Date();
        Date refreshTokenExpireDate = new Date();

        when(jwtTokenUtil.createAccessToken(userInfoForJwtRequestDto)).thenReturn(accessToken);
        when(jwtTokenUtil.createRefreshToken(userInfoForJwtRequestDto)).thenReturn(refreshToken);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC)).thenReturn(refreshTokenExpireDate);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC)).thenReturn(accessTokenExpireDate);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // when
        JwtResponseDto issuedJwtResponseDto = authService.issueJwt(userInfoForJwtRequestDto);

        // then
        assertThat(issuedJwtResponseDto.getAccessToken()).isEqualTo(accessToken);
        assertThat(issuedJwtResponseDto.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(issuedJwtResponseDto.getAccessTokenExpireDateTime()).isEqualTo(
            jwtTokenUtil.getExpireDate(JwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());
        assertThat(issuedJwtResponseDto.getRefreshTokenExpireDateTime()).isEqualTo(
            jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDateTime());

        verify(redisTemplate.opsForValue()).set("RT " + userInfoForJwtRequestDto.getMemberNo(), refreshToken,
            JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC,
            TimeUnit.MILLISECONDS);
    }

    @Test
    void reissueJwt() {
        // given
        String refreshToken = "zbc";

        ReissueJwtRequestDto reissueJwtRequestDto = new ReissueJwtRequestDto();
        reissueJwtRequestDto.setMemberNo(1);
        reissueJwtRequestDto.setAuthorities(List.of("ROLE_ADMIN"));
        reissueJwtRequestDto.setRefreshToken(refreshToken);

        String newAccessToken = "access-token";
        String newRefreshToken = "refresh-token";

        UserInfoForJwtRequestDto userInfoForJwtRequestDto = new UserInfoForJwtRequestDto();

        userInfoForJwtRequestDto.setMemberNo(reissueJwtRequestDto.getMemberNo());
        userInfoForJwtRequestDto.setEmail("fds@naver.com");
        userInfoForJwtRequestDto.setAuthorities(reissueJwtRequestDto.getAuthorities());


        Date accessTokenExpireDate = new Date();
        Date refreshTokenExpireDate = new Date();

        when(jwtTokenUtil.createRefreshToken(any())).thenReturn(newRefreshToken);
        when(jwtTokenUtil.createAccessToken(any())).thenReturn(newAccessToken);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC)).thenReturn(refreshTokenExpireDate);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC)).thenReturn(accessTokenExpireDate);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(redisTemplate.opsForValue().get("RT " + reissueJwtRequestDto.getMemberNo())).thenReturn(refreshToken);

        // when
        JwtResponseDto issuedJwtResponseDto = authService.reissueJwt(reissueJwtRequestDto);

        // then
        assertThat(issuedJwtResponseDto.getAccessToken()).isEqualTo(newAccessToken);
        assertThat(issuedJwtResponseDto.getRefreshToken()).isEqualTo(newRefreshToken);

    }

    @Test
    void reissueJwtNotFoundRefreshTokenException() {
        // given
        String refreshToken = "zbc";

        ReissueJwtRequestDto reissueJwtRequestDto = new ReissueJwtRequestDto();
        reissueJwtRequestDto.setMemberNo(1);
        reissueJwtRequestDto.setAuthorities(List.of("ROLE_ADMIN"));
        reissueJwtRequestDto.setRefreshToken(refreshToken);

        String newAccessToken = "access-token";
        String newRefreshToken = "refresh-token";

        UserInfoForJwtRequestDto userInfoForJwtRequestDto = new UserInfoForJwtRequestDto();

        userInfoForJwtRequestDto.setMemberNo(reissueJwtRequestDto.getMemberNo());
        userInfoForJwtRequestDto.setEmail("fds@naver.com");
        userInfoForJwtRequestDto.setAuthorities(reissueJwtRequestDto.getAuthorities());


        Date accessTokenExpireDate = new Date();
        Date refreshTokenExpireDate = new Date();

        when(jwtTokenUtil.createRefreshToken(userInfoForJwtRequestDto)).thenReturn(newRefreshToken);
        when(jwtTokenUtil.createAccessToken(userInfoForJwtRequestDto)).thenReturn(newAccessToken);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC)).thenReturn(refreshTokenExpireDate);
        when(jwtTokenUtil.getExpireDate(JwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC)).thenReturn(accessTokenExpireDate);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // when, then
        assertThatThrownBy(() -> authService.reissueJwt(reissueJwtRequestDto)).isInstanceOf(
            NotFoundRefreshTokenException.class).hasMessageContaining("해당 refresh token 이 존재하지 않습니다.");

    }
}