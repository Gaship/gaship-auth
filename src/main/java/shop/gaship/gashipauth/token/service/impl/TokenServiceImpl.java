package shop.gaship.gashipauth.token.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.service.TokenService;
import shop.gaship.gashipauth.util.JwtTokenUtil;
import shop.gaship.gashipauth.util.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token <br/>
 * fileName       : ToeknService <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtTokenUtil tokenUtil;

    @Override
    public JwtTokenDto createToken(SignInSuccessUserDetailsDto userDetailsDto) {
        String accessToken = tokenUtil.createAccessToken(userDetailsDto);
        String refreshToken = tokenUtil.createRefreshToken(userDetailsDto);

        return generateJwtToken(accessToken, refreshToken);
    }

    private JwtTokenDto generateJwtToken(String accessToken, String refreshToken) {
        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshToken(refreshToken);

        return jwtTokenDto;
    }
}
