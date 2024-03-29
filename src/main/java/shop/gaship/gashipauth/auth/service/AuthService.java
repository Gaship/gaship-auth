package shop.gaship.gashipauth.auth.service;

import shop.gaship.gashipauth.auth.dto.request.ReissueJwtRequestDto;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.dto.response.JwtResponseDto;

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
     * @param accessToken  인증 정보 access token.
     * @param refreshToken 인증 정보 refresh token.
     * @param memberNo     회원 번호.
     * @return 로그아웃에 대한 응답 정보 입니다.
     */
    void logout(String accessToken, String refreshToken, Integer memberNo);

    /**
     * Jwt 발급 받는 비즈니스 로직을 처리하는 메서드.
     *
     * @param userInfoDto 회원 정보(회원 번호, 권한).
     * @return 토큰 재발급에 대한 응답 정보.
     */
    JwtResponseDto issueJwt(UserInfoForJwtRequestDto userInfoDto);

    /**
     * Jwt 재발급 받는 비즈니스 로직을 처리하는 메서드.
     *
     * @param jwtDto 회원 정보(회원 번호, 권한).
     * @return 토큰 재발급에 대한 응답 반환.
     */
    JwtResponseDto reissueJwt(ReissueJwtRequestDto jwtDto);
}
