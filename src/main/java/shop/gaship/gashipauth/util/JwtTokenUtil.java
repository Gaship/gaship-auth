package shop.gaship.gashipauth.util;

import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    private static final int THIRTY_MINUTE_AT_SECONDS = 1800000;
    private static final int ONE_MONTH_AT_SECONDS = 1800000;

    private final Key createKey;

    public JwtTokenUtil(Key createKey) {
        this.createKey = createKey;
    }

    /**
     * methodName : generateAccessToken
     * author : 김민수
     * description : 사용자에게 부여 할 access token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createAccessToken(UserDetails userDetails) {
        return getToken(userDetails, THIRTY_MINUTE_AT_SECONDS);
    }

    /**
     * methodName : generateRefreshToken
     * author : 김민수
     * description : 사용자에게 부여 할 refresh token입니다.
     *
     * @param userDetails : 사용자 인증 정보
     * @return string
     */
    public String createRefreshToken(UserDetails userDetails) {
        return getToken(userDetails, ONE_MONTH_AT_SECONDS);
    }

    private String getToken(UserDetails userDetails, int seconds) {
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


    private Map<String, Object> makeJwtPayload(UserDetails userDetails, long expireTime) {
        GrantedAuthority authority =  userDetails.getAuthorities().stream()
            .findFirst()
            .orElseThrow();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        claims.put("role", authority);
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

    private Date getExpireDate(long seconds){
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + seconds);

        return expireTime;
    }
}
