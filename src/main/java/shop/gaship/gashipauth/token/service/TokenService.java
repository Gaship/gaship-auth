package shop.gaship.gashipauth.token.service;

import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;

/**
 * 토큰을 발급해주는 서비스가 반드시 구현해야할 인터페이스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public interface TokenService {

    /**
     * jwt토큰을 생성해줍니다.
     *
     * @param userDetailsDto 로그인에 성공한 회원의 정보가 담긴 객체입니다.
     * @return jwt 토큰을 반환합니다.
     */
    JwtTokenDto createToken(UserInfoForJwtRequestDto userDetailsDto);
}
