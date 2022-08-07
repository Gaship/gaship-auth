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
     * logout 관련 비즈니스 로직을 처리하는 메서드.
     *
     * @param accessToken 인증 정보 access token.
     * @param refreshToken 인증 정보 refresh token.
     * @param memberNo 회원 번호.
     * @return 로그아웃에 대한 응답 정보.
     */
    ResponseEntity<?> logout(String accessToken, String refreshToken, Integer memberNo);

    /**
     * Jwt 발급 받는 비즈니스 로직을 처리하는 메서드.
     *
     * @param userInfoDto 회원 정보(회원 번호, 권한).
     * @return 토큰 재발급에 대한 응답 정보.
     */
    ResponseEntity<?> issueJwt(UserInfoForJwtRequestDto userInfoDto);
}
