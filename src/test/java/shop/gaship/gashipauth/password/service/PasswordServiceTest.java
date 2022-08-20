package shop.gaship.gashipauth.password.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.password.dto.request.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipauth.password.dto.response.SuccessReissueResponse;
import shop.gaship.gashipauth.util.EmailSenderUtil;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@Import(PasswordService.class)
class PasswordServiceTest {

    @Autowired
    private PasswordService passwordService;

    @MockBean
    EmailSenderUtil emailSenderUtil;

    @Test
    void sendReissuePasswordByEmail() {
        doNothing().when(emailSenderUtil).sendMail(any(EmailSendDto.class));
        String email = "example@nhn.com";
        ReissuePasswordReceiveEmailDto dto = new ReissuePasswordReceiveEmailDto(email);

        SuccessReissueResponse result = passwordService.sendReissuePasswordByEmail(dto);

        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getReissuedPassword()).isNotBlank();
    }

    @Test
    void sendReissuePasswordByEmailFailure() {
        doThrow(EmailSendFailureException.class).when(emailSenderUtil).sendMail(any(EmailSendDto.class));
        String email = "example@nhn.com";
        ReissuePasswordReceiveEmailDto dto = new ReissuePasswordReceiveEmailDto(email);

        assertThatThrownBy(() ->
            passwordService.sendReissuePasswordByEmail(dto))
            .isInstanceOf(EmailSendFailureException.class);

    }
}
