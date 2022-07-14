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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.token.dto.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token <br/>
 * fileName       : JwtTokenUtil <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/09 <br/>
 * description    : 토큰을 발급해주는 본 구현 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/09           김민수               최초 생성              <br/>
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private static final long THIRTY_MINUTE_AT_MILLI_SEC = 1_800_000L;
    private static final long ONE_MONTH_AT_MILLI_SEC = 2_629_700_000L;
    public static final long  REDIS_EXPIRE_MAX_SECOND = THIRTY_MINUTE_AT_MILLI_SEC;
    public static final long CLIENT_EXPIRE_MAX_SECOND = ONE_MONTH_AT_MILLI_SEC;

    private final Key createKey;

    /**
     * methodName : generateAccessToken
     * author : 김민수
     * description : 사용자에게 부여 할 access token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createAccessToken(SignInSuccessUserDetailsDto userDetails) {
        return getToken(userDetails, THIRTY_MINUTE_AT_MILLI_SEC);
    }

    /**
     * methodName : generateRefreshToken
     * author : 김민수
     * description : 사용자에게 부여 할 refresh token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createRefreshToken(SignInSuccessUserDetailsDto userDetails) {
        return getToken(userDetails, ONE_MONTH_AT_MILLI_SEC);
    }

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
            .signWith(createKey)
            .setExpiration(Timestamp.valueOf(accessTokenExpireDate))
            .compact();
    }


    private Map<String, Object> makeJwtPayload(SignInSuccessUserDetailsDto userDetails, long expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getIdentifyNo());
        claims.put("role", userDetails.getAuthorities());
        claims.put("createAt", new Date().getTime());
        claims.put("expireAt", expireTime);

        return claims;
    }

    private Map<String, Object> makeJwtHeader() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        return headerMap;
    }

    private LocalDateTime getExpireDate(long milliSeconds){
        LocalDateTime expireTime = LocalDateTime.now();
        return expireTime.plus(Duration.ofMillis(milliSeconds));
    }
}
