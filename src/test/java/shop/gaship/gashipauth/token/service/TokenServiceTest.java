package shop.gaship.gashipauth.token.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.service.impl.TokenServiceImpl;
import shop.gaship.gashipauth.token.util.JwtTokenUtil;

/**
 * packageName    : shop.gaship.gashipauth.token.service <br/> fileName       : TokenServiceTest <br/> author         :
 * 김민수 <br/> date           : 2022/07/11 <br/> description    : <br/> ===========================================================
 * <br/> DATE              AUTHOR             NOTE                    <br/> -----------------------------------------------------------
 * <br/> 2022/07/11           김민수               최초 생성                         <br/>
 */
@ExtendWith({SpringExtension.class})
@Import(TokenServiceImpl.class)
class TokenServiceTest {

    //sut
    @Autowired
    TokenService tokenService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @Test
    void createToken() {
        // given
        UserInfoForJwtRequestDto userDetailsDto = new UserInfoForJwtRequestDto();
        String tmpAccessToken = "jfasdkljfaskljfa;jkdfkasl;jfa;fjkasdl";
        String tmpRefreshToken = "hjsdfkajkfshf8r_wef8eyqeruidasdassdasdas";

        given(jwtTokenUtil.createAccessToken(any()))
            .willReturn(tmpAccessToken);
        given(jwtTokenUtil.createRefreshToken(any()))
            .willReturn(tmpRefreshToken);

        //when
        JwtTokenDto tokens = tokenService.createToken(userDetailsDto);

        // then
        assertThat(tokens.getAccessToken())
            .isEqualTo(tmpAccessToken);
        assertThat(tokens.getRefreshToken())
            .isEqualTo(tmpRefreshToken);
    }
}
