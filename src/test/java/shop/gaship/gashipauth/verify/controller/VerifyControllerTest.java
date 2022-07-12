package shop.gaship.gashipauth.verify.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;
import shop.gaship.gashipauth.verify.service.VerifyService;

/**
 * packageName    : shop.gaship.gashipauth.verify.controller <br/>
 * fileName       : VerifyControllerTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@WebMvcTest(VerifyController.class)
class VerifyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    VerifyController verifyController;

    @MockBean
    VerifyService verifyService;

    @Test
    void requestEmailVerify() throws Exception {
        String testEmail = "example@nhn.com";
        given(verifyService.sendSignUpVerifyEmail(testEmail))
            .willReturn(true);

        mockMvc.perform(get("/securities/verify/email")
                .param("address", testEmail))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.requestStatus").value("success"));
    }

    @Test
    void requestEmailVerifyFailure() throws Exception {
        String testEmail = "example@nhn.com";
        given(verifyService.sendSignUpVerifyEmail(testEmail))
            .willReturn(false);

        mockMvc.perform(get("/securities/verify/email")
                .param("address", testEmail))
            .andExpect(result ->
                assertThat(result.getResolvedException().getClass()).isEqualTo(
                    EmailSendFailureException.class));
    }
}
