package shop.gaship.gashipauth.verify.advisor;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipauth.message.ErrorResponse;
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
@Slf4j
public class VerifyAdvisor {
    @ExceptionHandler({
        EmailSendFailureException.class,
        EmailVerificationImpossibleException.class,
    })
    public ResponseEntity<ErrorResponse> emailSendFailAdviser(RuntimeException exception){
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        RuntimeException.class,
        Exception.class
    })
    public ResponseEntity<ErrorResponse> anyExceptionAdvisor(Exception exception){
        log.error("VerifyAdvisor error cause : {0}, {1}", exception.getCause());
        log.error("VerifyAdvisor error message : {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }
}
