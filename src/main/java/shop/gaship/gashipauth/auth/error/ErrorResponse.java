package shop.gaship.gashipauth.auth.error;

/**
 * 에러발생시 ResponseEntity의 body에 담아줄 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
