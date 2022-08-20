package shop.gaship.gashipauth.exceptions.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipauth.exceptions.ErrorResponse;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;

/**
 * 인증관련 오류발생시 handling을 해주는 에러 핸들 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@RestControllerAdvice
@Slf4j
public class VerifyAdvisor {

    /**
     * 이메일 전송실패, 검증에 대한 실패를 잡아 전달하는 에러핸들러입니다.
     *
     * @param exception EmailSendFailureException 또는 EmailVerificationImpossibleException 객체입니다.
     * @return 요청을 보낸 클라이언트에게 던져줄 예외입니다.
     */
    @ExceptionHandler({EmailSendFailureException.class,
        EmailVerificationImpossibleException.class})
    public ResponseEntity<ErrorResponse> emailSendFailAdviser(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    /**
     * 예상치 못한 에러, 예외가 발생했을 때 처리할 에러핸들러입니다.
     *
     * @param exception 알 수 없는 예외, 에러 객체입니다.
     * @return 요청을 보낸 클라이언트에게 던져줄 예외 결과 값입니다.
     */
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<ErrorResponse> anyExceptionAdvisor(Exception exception) {
        log.error("VerifyAdvisor error cause : {0}, {1}", exception.getCause());
        log.error("VerifyAdvisor error message : {}", exception.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse(exception.getMessage()));
    }
}
