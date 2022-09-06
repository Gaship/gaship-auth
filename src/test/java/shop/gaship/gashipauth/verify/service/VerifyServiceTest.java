package shop.gaship.gashipauth.verify.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.config.ServerConfig;
import shop.gaship.gashipauth.util.EmailSenderUtil;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.dto.VerificationCodeDto;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;
import shop.gaship.gashipauth.verify.service.impl.VerifyServiceImpl;

/**
 * packageName    : shop.gaship.gashipauth.verify.service <br/> fileName       : VerifyServiceTest <br/> author
 * : 김민수 <br/> date           : 2022/07/13 <br/> description    : <br/> ===========================================================
 *  <br/> DATE              AUTHOR             NOTE                    <br/> -----------------------------------------------------------
 * <br/> 2022/07/13           김민수               최초 생성                         <br/>
 */
@ExtendWith({SpringExtension.class})
@Import({VerifyServiceImpl.class, ServerConfig.class})
class VerifyServiceTest {

    @Autowired
    VerifyService verifyService;

    @MockBean
    EmailSenderUtil emailSenderUtil;

    @MockBean
    RedisTemplate<String, String> redisTemplate;

    @Test
    void sendSignUpVerifyEmail() {
        // given
        SetOperations<String, String> setOperations = mock(SetOperations.class);
        doReturn(setOperations).when(redisTemplate).opsForSet();
        when(setOperations.add(anyString(), anyString())).thenReturn(1L);
        doNothing().when(emailSenderUtil)
                   .sendMail(any(EmailSendDto.class));

        // when
        VerificationCodeDto result = verifyService.sendSignUpVerifyEmail("abc");

        // then
        assertThat(result.getVerificationCode()).isNotNull();
    }

    @Test
    void approveVerificationEmail() {
        //given
        SetOperations<String, String> setOperations = mock(SetOperations.class);
        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.pop(anyString())).willReturn("true");

        //when
        boolean result =
            verifyService.approveVerificationEmail("123bd87b-c6bf-4e45-95c5-650ca76de779");

        assertThat(result).isTrue();
    }

    @Test
    @Disabled
    void approveVerificationEmailResultNullCase() {
        //given
        SetOperations<String, String> setOperations = mock(SetOperations.class);
        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.pop(anyString())).willReturn(null);

        assertThatThrownBy(() ->
            verifyService.approveVerificationEmail("123bd87b-c6bf-4e45-95c5-650ca76de779"))
            .isInstanceOf(EmailVerificationImpossibleException.class)
            .hasMessage("이메일 인증시간이 만료되거나, 검증이 불가능합니다.");
    }

    @Test
    void checkVerificationCode() {
        //given
        SetOperations<String, String> setOperations = mock(SetOperations.class);
        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.size(anyString())).willReturn(1L);

        //when
        boolean result =
            verifyService.checkVerificationCode("123bd87b-c6bf-4e45-95c5-650ca76de779");

        assertThat(result).isTrue();
    }

    @Test
    void checkVerificationCodeKeySizeZeroCase() {
        //given
        SetOperations<String, String> setOperations = mock(SetOperations.class);
        given(redisTemplate.opsForSet()).willReturn(setOperations);
        given(setOperations.size(anyString())).willReturn(0L);

        //when
        boolean result =
            verifyService.checkVerificationCode("123bd87b-c6bf-4e45-95c5-650ca76de779");

        assertThat(result).isFalse();
    }
}
