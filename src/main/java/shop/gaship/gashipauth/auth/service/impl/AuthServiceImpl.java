package shop.gaship.gashipauth.auth.service.impl;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
     * logout 관련 비즈니스 로직을 처리하는 메서드. logout시 accessToken을 Redis에 blackList로 저장, 기존 Redis에 있는
     * RefreshToken 삭제
     *
     * @param accessToken
     * @param refreshToken
     * @param memberNo
     * @return
     */
    @Override
    public ResponseEntity<?> logout(String accessToken, String refreshToken, Integer memberNo) {

        if (redisTemplate.opsForValue().get("RT " + memberNo) != null
            && redisTemplate.opsForValue().get("RT " + memberNo).equals(refreshToken)) {
            redisTemplate.delete("RT " + memberNo);
        } else {
            throw new NotFoundRefreshTokenException("해당 Refresh Token을 찾을 수 없습니다.");
        }

        redisTemplate.opsForValue()
            .set(accessToken, "logout", jwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC,
                TimeUnit.MILLISECONDS);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Jwt 발급 받는 비즈니스 로직을 처리하는 메서드 (Refresh Token 한달, AccessToken 30분 만료기간)
     *
     * @param userInfoDto
     * @return
     */
    @Override
    public ResponseEntity<?> issueJwt(UserInfoForJwtRequestDto userInfoDto) {

        Integer userNo = userInfoDto.getMemberNo();

        String refreshToken = jwtTokenUtil.createRefreshToken(userInfoDto);
        String accessToken = jwtTokenUtil.createAccessToken(userInfoDto);

        JwtResponseDto jwtTokenDto = new JwtResponseDto();

        jwtTokenDto.setRefreshToken(refreshToken);
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshTokenExpireDateTime(
            jwtTokenUtil.getExpireDate(jwtTokenUtil.ONE_MONTH_AT_MILLI_SEC).toInstant().atZone(
                ZoneId.systemDefault()).toLocalDateTime());
        jwtTokenDto.setAccessTokenExpireDateTime(
            jwtTokenUtil.getExpireDate(jwtTokenUtil.THIRTY_MINUTE_AT_MILLI_SEC).toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());

        redisTemplate.opsForValue()
            .set("RT " + userNo, refreshToken, jwtTokenUtil.ONE_MONTH_AT_MILLI_SEC,
                TimeUnit.MILLISECONDS);

        return ResponseEntity.ok(jwtTokenDto);
    }
}
