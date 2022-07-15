package shop.gaship.gashipauth.verify.exception;

/**
 * 이메일을 인증 할 수 없을 때 발생하는 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class EmailVerificationImpossibleException extends RuntimeException {
    private static final String MESSAGE = "이메일 인증시간이 만료되거나, 검증이 불가능합니다.";
    public EmailVerificationImpossibleException() {
        super(MESSAGE);
    }
}
