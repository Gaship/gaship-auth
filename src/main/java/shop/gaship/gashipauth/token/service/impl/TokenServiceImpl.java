package shop.gaship.gashipauth.token.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.service.TokenService;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 *
 * 토큰을 발급해주는 서비스 구현 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtTokenUtil tokenUtil;

    @Override
    public JwtTokenDto createToken(UserInfoForJwtRequestDto userDetailsDto) {
        String accessToken = tokenUtil.createAccessToken(userDetailsDto);
        String refreshToken = tokenUtil.createRefreshToken(userDetailsDto);

        return new JwtTokenDto(accessToken, refreshToken);
    }
}
