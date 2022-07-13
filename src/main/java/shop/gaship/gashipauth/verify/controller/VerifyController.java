package shop.gaship.gashipauth.verify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.util.dto.RequestSuccessDto;
import shop.gaship.gashipauth.verify.exception.EmailSendFailureException;
import shop.gaship.gashipauth.verify.exception.EmailVerificationImpossibleException;
import shop.gaship.gashipauth.verify.service.VerifyService;

/**
 * packageName    : shop.gaship.gashipauth.verify.controller <br/>
 * fileName       : VerifyController <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : 이메일 인증과같은 제 3자 서비스를 이용하여 인증하기 위한 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@RestController
@RequestMapping("/securities/verify")
@RequiredArgsConstructor
public class VerifyController {
    private final VerifyService verifyService;


    @GetMapping("/email")
    public RequestSuccessDto requestEmailVerify(@RequestParam String address) {
        boolean isSend = verifyService.sendSignUpVerifyEmail(address);

        if (!isSend) {
            throw new EmailSendFailureException("서버의 오류로 인해 전송에 실패했습니다.");
        }

        return new RequestSuccessDto();
    }

    @GetMapping("/email/{verifyCode}")
    public RequestSuccessDto verifyEmail(@PathVariable String verifyCode) {
        boolean isVerified = verifyService.approveVerificationEmail(verifyCode);

        if (!isVerified) {
            throw new EmailVerificationImpossibleException();
        }

        return new RequestSuccessDto();
    }
}
