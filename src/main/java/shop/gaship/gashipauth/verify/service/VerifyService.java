package shop.gaship.gashipauth.verify.service;

/**
 * packageName    : shop.gaship.gashipauth.verify.service <br/>
 * fileName       : VerifyService <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public interface VerifyService {
    boolean sendSignUpVerifyEmail(String receiverEmail);
}
