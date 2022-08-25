package shop.gaship.gashipauth.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipauth.auth.dto.request.ReissueJwtRequestDto;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;

/**
 * @author : 조재철
 * @since 1.0
 */
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @Test
    void logout() throws Exception {
        // given
        doNothing().when(authService).logout(any(), any(), any());

        Integer memberNo = 1;

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(memberNo);

        // when, then
        mockMvc.perform(
                   post("/securities/logout")
                       .header("X-AUTH-ACCESS-TOKEN", "a")
                       .header("X-AUTH-REFRESH-TOKEN", "b")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(authService).logout(any(), any(), any());
    }

    @Test
    void issueJwt() throws Exception {
        // given
        UserInfoForJwtRequestDto userInfoForJwtRequestDto = new UserInfoForJwtRequestDto();
        userInfoForJwtRequestDto.setMemberNo(1);
        userInfoForJwtRequestDto.setEmail("dfksl@naver.com");
        userInfoForJwtRequestDto.setAuthorities(List.of("ROLE_ADMIN"));

        when(authService.issueJwt(userInfoForJwtRequestDto)).thenReturn(any());

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userInfoForJwtRequestDto);

        // when, then
        mockMvc.perform(
                   post("/securities/issue-token")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(authService).issueJwt(any(UserInfoForJwtRequestDto.class));
    }

    @Test
    void reissueJwt() throws Exception {
        // given
        ReissueJwtRequestDto reissueJwtRequestDto = new ReissueJwtRequestDto();
        reissueJwtRequestDto.setRefreshToken("REFRESH_TOKEN");
        reissueJwtRequestDto.setMemberNo(1);
        reissueJwtRequestDto.setAuthorities(List.of("ROLE_ADMIN"));

        when(authService.reissueJwt(reissueJwtRequestDto)).thenReturn(any());

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(reissueJwtRequestDto);

        // when, then
        mockMvc.perform(
                   post("/securities/reissue-token")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(content))
               .andExpect(status().isOk());

        verify(authService).reissueJwt(any(ReissueJwtRequestDto.class));
    }
}