package shop.gaship.gashipauth.password.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipauth.password.dto.request.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipauth.password.dto.response.SuccessReissueResponse;
import shop.gaship.gashipauth.password.service.PasswordService;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@WebMvcTest(PasswordController.class)
class PasswordControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PasswordService passwordService;

    @Test
    @DisplayName("비밀번호 재발급 : 성공")
    void passwordReissueRequest() throws Exception {
        String email = "example@nhn.com";
        ReissuePasswordReceiveEmailDto requestDto =
            new ReissuePasswordReceiveEmailDto(email);
        String renewalPassword = RandomStringUtils.randomAlphanumeric(15);

        given(passwordService.sendReissuePasswordByEmail(any(ReissuePasswordReceiveEmailDto.class)))
            .willReturn(new SuccessReissueResponse(email, renewalPassword));

        mockMvc.perform(post("/securities/password/reissue")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(new ObjectMapper().writeValueAsString(requestDto))
                   .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.email").value(email))
               .andExpect(jsonPath("$.reissuedPassword").value(renewalPassword))
               .andDo(print());
    }

    @Test
    @DisplayName("비밀번호 재발급 : 실패")
    void passwordReissueRequestFailure() throws Exception {
        String email = "example@nhn.com";
        String errorMessage = "이메일 발송에 실패했습니다. 사유 : 알 수 없음";
        ReissuePasswordReceiveEmailDto requestDto =
            new ReissuePasswordReceiveEmailDto(email);

        given(passwordService.sendReissuePasswordByEmail(any(ReissuePasswordReceiveEmailDto.class)))
            .willThrow(new EmailSendFailureException("알 수 없음"));

        mockMvc.perform(post("/securities/password/reissue")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(new ObjectMapper().writeValueAsString(requestDto))
                   .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.message").value(errorMessage))
               .andDo(print());
    }
}
