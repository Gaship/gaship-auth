package shop.gaship.gashipauth.token.exceptions;

/**
 * 요청에 대한 응답할 데이터가 존재하지 않을시 발생할 에러 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
public class NoResponseDataException extends RuntimeException {

    public NoResponseDataException(String message) {
        super(message);
    }
}
