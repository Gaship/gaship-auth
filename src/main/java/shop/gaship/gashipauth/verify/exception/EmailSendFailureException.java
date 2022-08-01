package shop.gaship.gashipauth.verify.exception;

/**
 * 이메일 전송이 실패하였을 경우에 발생하는 예외입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public class EmailSendFailureException extends RuntimeException {
    public EmailSendFailureException(String message) {
        super("이메일 발송에 실패했습니다. 사유 : " + message);
    }
}
