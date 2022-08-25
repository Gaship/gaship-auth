package shop.gaship.gashipauth.auth.exception;

/**
 * RefreshToken을 Redis에서 찾을 수 없을때 발생하는 예외 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
public class NotFoundRefreshTokenException extends RuntimeException {

    public static final String MESSAGE = "해당 refresh token 이 존재하지 않습니다.";

    public NotFoundRefreshTokenException() {
        super(MESSAGE);
    }
}
