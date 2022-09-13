package shop.gaship.gashipauth.auth.service.impl;

import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.auth.dto.request.ReissueJwtRequestDto;
import shop.gaship.gashipauth.auth.exception.NotFoundRefreshTokenException;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.dto.response.JwtResponseDto;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 * Auth 관련 비즈니스 로직 처리를 하는 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final RedisTemplate redisTemplate;

    /**
     * logout 관련 비즈니스 로직을 처리하는 메서드. logout 시 accessToken 을
     * Redis 에 blackList 로 저장, 기존 Redis 에 있는 RefreshToken 삭제.
     *
     * @param accessToken  인증 정보 access token.
     * @param refreshToken 인증 정보 refresh token.
     * @param memberNo     회원 번호.
     * @return 로그아웃 응답 정보 반환.
     */
    @Override
    public void logout(String accessToken, String refreshToken, Integer memberNo) {

        String token = (String) redisTemplate.opsForValue().get("RT " + memberNo);

        if (refreshToken.equals(token)) {
            redisTemplate.delete("RT " + memberNo);
        } else {
            throw new NotFoundRefreshTokenException();
        }

        redisTemplate.opsForValue()
                     .set(accessToken, "logout", JwtTokenUtil.ONE_DAY_AT_MILLI_SEC,
                         TimeUnit.MILLISECONDS);
    }

    /**
     * Jwt 발급 받는 비즈니스 로직을 처리하는 메서드 (Refresh Token 한달, AccessToken 30분 만료기간).
     *
     * @param userInfoDto 회원 정보(회원 번호, 권한).
     * @return 토큰 발급에 대한 응답 반환.
     */
    @Override
    public JwtResponseDto issueJwt(UserInfoForJwtRequestDto userInfoDto) {

        String refreshToken = jwtTokenUtil.createRefreshToken(userInfoDto);
        String accessToken = jwtTokenUtil.createAccessToken(userInfoDto);

        JwtResponseDto jwtTokenDto = new JwtResponseDto();

        jwtTokenDto.setRefreshToken(refreshToken);
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshTokenExpireDateTime(
                jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDateTime());
        jwtTokenDto.setAccessTokenExpireDateTime(
                jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_DAY_AT_MILLI_SEC).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

        redisTemplate.opsForValue()
                    .set("RT " + userInfoDto.getMemberNo(),
                         refreshToken, JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC,
                         TimeUnit.MILLISECONDS);

        return jwtTokenDto;
    }

    /**
     * Jwt 재발급 받는 비즈니스 로직을 처리하는 메서드.
     *
     * @param reissueJwtRequestDto 회원 정보(회원 번호, 권한).
     * @return 토큰 재발급에 대한 응답 반환.
     */
    @Override
    public JwtResponseDto reissueJwt(ReissueJwtRequestDto reissueJwtRequestDto) {
        if (!Objects.equals(reissueJwtRequestDto.getRefreshToken(),
                redisTemplate.opsForValue().get("RT " + reissueJwtRequestDto.getMemberNo()))) {
            throw new NotFoundRefreshTokenException();
        }

        UserInfoForJwtRequestDto userInfoForJwtRequestDto = new UserInfoForJwtRequestDto();

        userInfoForJwtRequestDto.setMemberNo(reissueJwtRequestDto.getMemberNo());
        userInfoForJwtRequestDto.setAuthorities(reissueJwtRequestDto.getAuthorities());

        String refreshToken = jwtTokenUtil.createRefreshToken(userInfoForJwtRequestDto);
        String accessToken = jwtTokenUtil.createAccessToken(userInfoForJwtRequestDto);

        JwtResponseDto jwtTokenDto = new JwtResponseDto();

        jwtTokenDto.setRefreshToken(refreshToken);
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshTokenExpireDateTime(
                jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDateTime());
        jwtTokenDto.setAccessTokenExpireDateTime(
                jwtTokenUtil.getExpireDate(JwtTokenUtil.ONE_DAY_AT_MILLI_SEC).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime());

        redisTemplate.opsForValue()
                     .set("RT " + reissueJwtRequestDto.getMemberNo(),
                         refreshToken, JwtTokenUtil.ONE_MONTH_AT_MILLI_SEC,
                         TimeUnit.MILLISECONDS);

        return jwtTokenDto;

    }
}
