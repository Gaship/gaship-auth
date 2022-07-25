package shop.gaship.gashipauth.auth.service;

import org.springframework.http.ResponseEntity;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;

/**
 * The interface Auth service.
 *
 * @author : 조재철
 * @since 1.0
 */
public interface AuthService {

    /**
     * logout 관련 비즈니스 로직을 처리하는 메서드
     *
     * @param accessToken
     * @param refreshToken
     * @param memberNo
     * @return response entity
     */
    ResponseEntity<?> logout(String accessToken, String refreshToken, Integer memberNo);

    /**
     * Jwt 발급 받는 비즈니스 로직을 처리하는 메서드
     *
     * @param userInfoDto
     * @return
     */
    ResponseEntity<?> issueJwt(UserInfoForJwtRequestDto userInfoDto);
}
