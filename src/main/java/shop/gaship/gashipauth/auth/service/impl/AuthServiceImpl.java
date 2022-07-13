package shop.gaship.gashipauth.auth.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.auth.dto.Response;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 * packageName    : shop.gaship.gashipauth.token.service.impl fileName       : AuthServiceImpl
 * author         : jo date           : 2022/07/12 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2022/07/12        jo       최초 생성
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final Response response;
    private final RedisTemplate redisTemplate;


    @Override
    public ResponseEntity<?> logout(String token) {
        if (!jwtTokenUtil.validateToken(token)) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        String email = jwtTokenUtil.getEmail(token);

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + email) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + email);
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = new Date().getTime() - jwtTokenUtil.getExpiredDate(token).getTime();

        redisTemplate.opsForValue()
            .set(token, "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success();
    }
}
