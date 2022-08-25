package shop.gaship.gashipauth.token.util;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.config.SecureManagerConfig;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;

/**
 * Jwt 토큰 생성에 관한 유틸 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    public static final long THIRTY_MINUTE_AT_MILLI_SEC = 1_800_000L;

    public static final long ONE_MONTH_AT_MILLI_SEC = 2_629_700_000L;

    private final SecureManagerConfig secureManagerConfig;

    /**
     * AccessToken을 만드는 메서드.
     *
     * @param userDetails 회원 정보 (회원번호, 권한).
     * @return 생성한 access token 을 반환.
     */
    public String createAccessToken(UserInfoForJwtRequestDto userDetails) {
        return getToken(userDetails, THIRTY_MINUTE_AT_MILLI_SEC);
    }

    /**
     * RefreshToken을 만드는 메서드.
     *
     * @param userDetails 회원 정보 (회원번호, 권한).
     * @return 생성한 refresh token 을 반환.
     */
    public String createRefreshToken(UserInfoForJwtRequestDto userDetails) {
        return getToken(userDetails, ONE_MONTH_AT_MILLI_SEC);
    }

    /**
     * header, payload, signature를 만들어 jwt token을 만드는 메서드.
     *
     * @param userDetails 회원 정보 (회원번호, 권한).
     * @param seconds     시간(초).
     * @return access token을 반환.
     */
    private String getToken(UserInfoForJwtRequestDto userDetails, long seconds) {
        Map<String, Object> header = makeJwtHeader();
        Date accessTokenExpireDate = getExpireDate(seconds);
        Map<String, Object> payload = makeJwtPayload(userDetails, accessTokenExpireDate.getTime());

        return Jwts.builder()
                   .setHeader(header)
                   .setClaims(payload)
                   .signWith(secureManagerConfig.tokenKey())
                   .setExpiration(accessTokenExpireDate)
                   .compact();
    }

    /**
     * jwt의 payload를 만드는 메서드.
     *
     * @param userDetails 회원 정보 (회원번호, 권한).
     * @param expireTime  토큰 만료 시간.
     * @return 토큰의 payload 반환.
     */
    private Map<String, Object> makeJwtPayload(UserInfoForJwtRequestDto userDetails,
            long expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getMemberNo());
        claims.put("role", userDetails.getAuthorities());
        claims.put("createAt", new Date().getTime());
        claims.put("expireAt", expireTime);

        return claims;
    }

    /**
     * jwt의 header를 만드는 메서드.
     *
     * @return 토큰의 header 반환.
     */
    private Map<String, Object> makeJwtHeader() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        return headerMap;
    }

    /**
     * jwt 인증 만료기간을 만드는 메서드.
     *
     * @param seconds 시간(초).
     * @return 토크은 만료 기간 반환.
     */
    public Date getExpireDate(long seconds) {
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + seconds);

        return expireTime;
    }

}
