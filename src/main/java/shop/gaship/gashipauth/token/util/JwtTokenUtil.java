package shop.gaship.gashipauth.token.util;

import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.config.SecureManagerConfig;
import shop.gaship.gashipauth.token.dto.SignInSuccessUserDetailsDto;

/**
 *
 * 토큰을 발급해주는 본 구현 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Component
public class JwtTokenUtil {
    private static final long THIRTY_MINUTE_AT_MILLI_SEC = 1_800_000L;
    private static final long ONE_MONTH_AT_MILLI_SEC = 2_629_700_000L;
    public static final long  REDIS_EXPIRE_MAX_SECOND = THIRTY_MINUTE_AT_MILLI_SEC;
    public static final long CLIENT_EXPIRE_MAX_SECOND = ONE_MONTH_AT_MILLI_SEC;

    private final Key tokenKey;

    public JwtTokenUtil(SecureManagerConfig secureManagerConfig) {
        this.tokenKey = secureManagerConfig.tokenKey();
    }

    /**
     * 사용자에게 부여 할 access token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createAccessToken(SignInSuccessUserDetailsDto userDetails) {
        return getToken(userDetails, THIRTY_MINUTE_AT_MILLI_SEC);
    }

    /**
     * 사용자에게 부여 할 refresh token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createRefreshToken(SignInSuccessUserDetailsDto userDetails) {
        return getToken(userDetails, ONE_MONTH_AT_MILLI_SEC);
    }

    /**
     * Jws를 이용하여 Jwt토큰을 얻어내는 메서드입니다.
     *
     * @param userDetails  로그인한 회원의 정보가 담긴 객체입니다.
     * @param milliSeconds 토큰을 파기할 시간입니다.(milli seconds 단위)
     * @return 토큰 문자열
     */
    private String getToken(SignInSuccessUserDetailsDto userDetails, long milliSeconds) {
        Map<String, Object> header = makeJwtHeader();
        LocalDateTime accessTokenExpireDate = getExpireDate(milliSeconds);
        long milliSecondsExpireDate = accessTokenExpireDate
            .atZone(ZoneId.of("Asia/Tokyo"))
            .toInstant().toEpochMilli();
        Map<String, Object> payload = makeJwtPayload(userDetails, milliSecondsExpireDate);

        return Jwts.builder()
            .setHeader(header)
            .setClaims(payload)
            .signWith(tokenKey)
            .setExpiration(Timestamp.valueOf(accessTokenExpireDate))
            .compact();
    }


    /**
     * JWt 토큰의 payload를 생성합니다
     *
     * @param userDetails  로그인한 회원의 정보가 담긴 객체입니다.
     * @param expireTime  파기될 시간입니다 (milli seconds 단위).
     * @return the map
     */
    private Map<String, Object> makeJwtPayload(SignInSuccessUserDetailsDto userDetails, long expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getIdentifyNo());
        claims.put("role", userDetails.getAuthorities());
        claims.put("createAt", new Date().getTime());
        claims.put("expireAt", expireTime);

        return claims;
    }

    /**
     * Jwt 토큰의 헤더를 만듭니다.
     *
     * @see Map Map
     * @return Map 형태로 header 정보를 담은 객체가 반환됩니다.
     */
    private Map<String, Object> makeJwtHeader() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        return headerMap;
    }

    /**
     * 밀리초를 이용하여 파기 일자를 얻어냅니다.
     *
     * @param milliSeconds 현시간에서 더할 파기시간입니다. (milli seconds 단위)
     * @return 토큰을 파기할 시간값을 가진 LocalDateTime 객체입니다.
     */
    private LocalDateTime getExpireDate(long milliSeconds){
        LocalDateTime expireTime = LocalDateTime.now();
        return expireTime.plus(Duration.ofMillis(milliSeconds));
    }
}
