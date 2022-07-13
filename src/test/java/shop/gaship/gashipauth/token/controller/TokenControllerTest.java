package shop.gaship.gashipauth.token.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.dummy.JwtTokenDtoDummy;
import shop.gaship.gashipauth.token.dummy.SignInSuccessUserDetailsDtoDummy;
import shop.gaship.gashipauth.token.service.TokenService;
import shop.gaship.gashipauth.util.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token.controller <br/>
 * fileName       : TokenControllerTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@WebMvcTest(TokenController.class)
class TokenControllerTest {
    // mock mvc
    @Autowired
    MockMvc mockMvc;
    // sut
    @Autowired
    TokenController tokenController;

    @MockBean
    TokenService tokenService;

    @Test
    void tokenGenerateToken() throws Exception {
        JwtTokenDto dummyToken = JwtTokenDtoDummy.dummy();
        SignInSuccessUserDetailsDto userDetailDummy = SignInSuccessUserDetailsDtoDummy.dummy();
        given(tokenService.createToken(any(SignInSuccessUserDetailsDto.class)))
            .willReturn(dummyToken);

        String contentBody =
            new ObjectMapper().writeValueAsString(userDetailDummy);

        mockMvc.perform(post("/securities/issue-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.accessToken").value(dummyToken.getAccessToken()))
            .andExpect(jsonPath("$.refreshToken").value(dummyToken.getRefreshToken()));
    }
}
