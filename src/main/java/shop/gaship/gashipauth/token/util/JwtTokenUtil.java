package shop.gaship.gashipauth.token.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.token.dto.SignInSuccessUserDetailsDto;

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

    private String getToken(SignInSuccessUserDetailsDto userDetails, long seconds) {
        Map<String, Object> header = makeJwtHeader();
        Date accessTokenExpireDate = getExpireDate(seconds);
        Map<String, Object> payload = makeJwtPayload(userDetails, accessTokenExpireDate.getTime());

        return Jwts.builder()
            .setHeader(header)
            .setClaims(payload)
            .signWith(createKey)
            .setExpiration(accessTokenExpireDate)
            .compact();
    }


    private Map<String, Object> makeJwtPayload(SignInSuccessUserDetailsDto userDetails, long expireTime) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userDetails.getIdentifyNo());
        claims.put("email", userDetails.getEmail());
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

    private Date getExpireDate(long seconds) { // FIXME : 메서드 이름이 set이 되는게 맞지 않을까?
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + seconds);

        return expireTime;
    }

    public Date getExpiredDate(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(createKey).parseClaimsJws(token);

        return claims.getBody().getExpiration();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(createKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(createKey).parseClaimsJws(token).getBody()
            .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}
