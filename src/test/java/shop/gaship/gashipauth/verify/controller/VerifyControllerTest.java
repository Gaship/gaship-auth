package shop.gaship.gashipauth.verify.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shop.gaship.gashipauth.verify.dto.VerificationCodeDto;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;
import shop.gaship.gashipauth.verify.service.VerifyService;

/**
 * packageName    : shop.gaship.gashipauth.verify.controller <br/> fileName       : VerifyControllerTest <br/> author
 *      : 김민수 <br/> date           : 2022/07/12 <br/> description    : <br/> ===========================================================
 *  <br/> DATE              AUTHOR             NOTE                    <br/> -----------------------------------------------------------
 * <br/> 2022/07/12           김민수               최초 생성                         <br/>
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
    @DisplayName("이메일 인증 정상 요청")
    void requestEmailVerify() throws Exception {
        String testEmail = "example@nhn.com";
        given(verifyService.sendSignUpVerifyEmail(testEmail))
            .willReturn(new VerificationCodeDto("12341234"));

        mockMvc.perform(get("/securities/verify/email")
                   .param("address", testEmail))
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.verificationCode").value("12341234"));
    }

    @Test
    @DisplayName("이메일 인증 요청 실패 : 이메일 형식이 다른거나, 전송요청이 실패했을때 ")
    void requestEmailVerifyFailure() throws Exception {
        String testEmail = "example@nhn.com";
        given(verifyService.sendSignUpVerifyEmail(testEmail))
            .willThrow(new EmailSendFailureException("인증 이메일 전송에 실패했습니다."));

        mockMvc.perform(get("/securities/verify/email")
                   .param("address", testEmail))
               .andExpect(status().is4xxClientError())
               .andExpect(result ->
                   assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(
                       EmailSendFailureException.class));
    }

    @Test
    @DisplayName("이메일 인증 검증 확인 성공")
    void verifyEmailTest() throws Exception {
        String verifyCode = "easd-123-12312-1sdad";
        given(verifyService.approveVerificationEmail(verifyCode))
            .willReturn(true);

        mockMvc.perform(put("/securities/verify/email/{verifyCode}", verifyCode))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.requestStatus").value("success"));
    }

    @Test
    @DisplayName("이메일 검증 확인 실패 : 검증결과가 false일 경우")
    void verifyEmailFailTest() throws Exception {
        String verifyCode = "easd-123-12312-1sdad";
        given(verifyService.approveVerificationEmail(verifyCode))
            .willReturn(false);

        mockMvc.perform(put("/securities/verify/email/{verifyCode}", verifyCode))
               .andExpect(status().is4xxClientError())
               .andExpect(result ->
                   assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(
                       EmailVerificationImpossibleException.class))
               .andExpect(jsonPath("$.message")
                   .value("이메일 인증시간이 만료되거나, 검증이 불가능합니다."));
    }

    @Test
    @DisplayName("이메일 검증 확인 실패 : 이미 인증이 되었거나, 인증이 존재하지 않는경우")
    void verifyEmailThrowExceptionTest() throws Exception {
        String verifyCode = "easd-123-12312-1sdad";
        given(verifyService.approveVerificationEmail(verifyCode))
            .willThrow(new EmailVerificationImpossibleException());

        mockMvc.perform(put("/securities/verify/email/{verifyCode}", verifyCode))
               .andExpect(status().is4xxClientError())
               .andExpect(result ->
                   assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(
                       EmailVerificationImpossibleException.class))
               .andExpect(jsonPath("$.message")
                   .value("이메일 인증시간이 만료되거나, 검증이 불가능합니다."));
    }

    @Test
    @DisplayName("이메일 검증 확인 성공 : 이미 인증이 되었거나, 인증이 되지않 않는경우")
    void alreadyVerifiedCheck() throws Exception {
        String verifyCode = "easd-123-12312-1sdad";
        given(verifyService.removeVerificationCode(verifyCode))
            .willReturn(true);

        mockMvc.perform(get("/securities/verify/email/{verifyCode}", verifyCode))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.status")
                   .value(true));
    }

    @Test
    @DisplayName("이메일 검증 확인 실패 : 이미 인증이 되었거나, 인증이 존재하지 않는경우")
    void alreadyVerifiedCheckExceptionTest() throws Exception {
        String verifyCode = "easd-123-12312-1sdad";
        given(verifyService.removeVerificationCode(verifyCode))
            .willThrow(new EmailVerificationImpossibleException());

        mockMvc.perform(get("/securities/verify/email/{verifyCode}", verifyCode))
               .andExpect(status().is4xxClientError())
               .andExpect(result ->
                   assertThat(Objects.requireNonNull(result.getResolvedException()).getClass()).isEqualTo(
                       EmailVerificationImpossibleException.class))
               .andExpect(jsonPath("$.message")
                   .value("이메일 인증시간이 만료되거나, 검증이 불가능합니다."));
    }
}
