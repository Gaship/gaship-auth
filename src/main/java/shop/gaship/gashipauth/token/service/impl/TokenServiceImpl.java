package shop.gaship.gashipauth.token.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.service.TokenService;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;
import shop.gaship.gashipauth.token.dto.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token <br/>
 * fileName       : ToeknServiceImpl <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : 토큰을 발급해주는 서비스 구현 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성              <br/>
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtTokenUtil tokenUtil;

    @Override
    public JwtTokenDto createToken(SignInSuccessUserDetailsDto userDetailsDto) {
        String accessToken = tokenUtil.createAccessToken(userDetailsDto);
        String refreshToken = tokenUtil.createRefreshToken(userDetailsDto);

        return new JwtTokenDto(accessToken, refreshToken);
    }
}
