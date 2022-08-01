package shop.gaship.gashipauth.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.gaship.gashipauth.auth.error.ErrorResponse;
import shop.gaship.gashipauth.auth.exception.NotFoundRefreshTokenException;

/**
 * 전반적인 에러를 처리하는 Controller Advice 클래스이다.
 *
 * @author : 조재철
 * @since 1.0
 */
@RestControllerAdvice("AuthController")
public class ErrorController {

    /**
     * NotFoundRefreshTokenException 예외를 처리하는 메서드.
     *
     * @param e refresh tokend을 찾지 못했을때 에러.
     * @return refreshtoken을 찾지 못하였을때 응답 반환.
     */
    @ExceptionHandler(NotFoundRefreshTokenException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundRefreshTokenException(
            NotFoundRefreshTokenException e) {

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(e.getMessage()));
    }
}
