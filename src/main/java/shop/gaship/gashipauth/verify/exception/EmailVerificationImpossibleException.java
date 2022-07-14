package shop.gaship.gashipauth.verify.exception;

/**
 * packageName    : shop.gaship.gashipauth.verify.exception <br/>
 * fileName       : EmailVerifictionImpossibleExcpetion <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : 이메일을 인증 할 수 없을 때 발생하는 예외입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public class EmailVerificationImpossibleException extends RuntimeException {
    private static final String MESSAGE = "이메일 인증시간이 만료되거나, 검증이 불가능합니다.";
    public EmailVerificationImpossibleException() {
        super(MESSAGE);
    }
}
