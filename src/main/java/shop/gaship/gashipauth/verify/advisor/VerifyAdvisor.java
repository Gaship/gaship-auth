package shop.gaship.gashipauth.verify.advisor;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;

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
@RestControllerAdvice
public class VerifyAdvisor {
    @ExceptionHandler({
        EmailSendFailureException.class,
        EmailVerificationImpossibleException.class,
        RuntimeException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> emailSendFailAdviser(RuntimeException sendFailureException){
        return Map.of("requestStatus", "failure",
                "message", sendFailureException.getMessage());
    }
}
