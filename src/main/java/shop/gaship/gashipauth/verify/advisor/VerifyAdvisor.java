package shop.gaship.gashipauth.verify.advisor;

import java.util.Map;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;

/**
 * packageName    : shop.gaship.gashipauth.verify.advisor <br/>
 * fileName       : VerifyAdvisor <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
@ControllerAdvice
public class VerifyAdvisor {
    @ExceptionHandler(EmailSendFailureException.class)
    public Map<String, Object> emailSendFailAdviser(EmailSendFailureException sendFailureException){
        return Map.of("requestStatus", "failure",
                "message", sendFailureException.getMessage());
    }
}
