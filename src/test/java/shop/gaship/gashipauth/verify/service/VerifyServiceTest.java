package shop.gaship.gashipauth.verify.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;
import shop.gaship.gashipauth.verify.service.impl.VerifyServiceImpl;
import shop.gaship.gashipauth.verify.util.EmailSenderUtil;

/**
 * packageName    : shop.gaship.gashipauth.verify.service <br/>
 * fileName       : VerifyServiceTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
@ExtendWith({SpringExtension.class})
@Import({VerifyServiceImpl.class})
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
        SetOperations<String, String> valueOperations = mock(SetOperations.class);
        doReturn(valueOperations).when(redisTemplate).opsForSet();
        when(valueOperations.add(anyString(), anyString())).thenReturn(1L);
        doNothing().when(emailSenderUtil)
            .sendMail(any(EmailSendDto.class));

        // when
        boolean result = verifyService.sendSignUpVerifyEmail("abc");

        // then
        assertThat(result).isTrue();
    }
}
