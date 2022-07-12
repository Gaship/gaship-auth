package shop.gaship.gashipauth.verify.exception;

/**
 * packageName    : shop.gaship.gashipauth.verify.exception <br/>
 * fileName       : EmailSendFailureException <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
public class EmailSendFailureException extends RuntimeException {
    public EmailSendFailureException(String message) {
        super("이메일 발송에 실패했습니다. 사유 : " + message);
    }
}
