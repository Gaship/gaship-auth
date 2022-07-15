package shop.gaship.gashipauth.verify.service;

/**
 * 회원 또는 고객의 인가정보를 검증하기위해 구현해야하는 서비스 인터페이스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
public interface VerifyService {
    /**
     * 회원가입을 위해서 이메일인증을하는 메서드입니다.
     *
     * @param receiverEmail 인증을 할 대상 이메일입니다.
     * @return 이메일에 인증정보를 전송했다면 true를 반환합니다.
     */
    boolean sendSignUpVerifyEmail(String receiverEmail);

    /**
     * 이메일 검증 코드를 확인하는 메서드입니다
     *
     * @param verifyCode 인증을 위해 전송했던 코드입니다.
     * @return 인증에 완료하면 true를 반환합니다.
     */
    boolean approveVerificationEmail(String verifyCode);
}
