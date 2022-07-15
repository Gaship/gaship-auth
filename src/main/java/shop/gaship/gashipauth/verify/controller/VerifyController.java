package shop.gaship.gashipauth.verify.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
 * 이메일 인증과같은 제 3자 서비스를 이용하여 인증하기 위한 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/securities/verify")
@RequiredArgsConstructor
public class VerifyController {
    private final VerifyService verifyService;


    /**
     * 회원가입 이메일 검증 요청시 인증 이메일을 전송해주는 컨트롤러입니다.
     *
     * @param address 회원가입 할 유저의 이메일 주소입니다.
     * @return 요청에 성공했다는 응답을 알리기위한 dto객체입니다.
     */
    @GetMapping("/email")
    public ResponseEntity<RequestSuccessDto> requestEmailVerify(@RequestParam String address) {
        boolean isSend = verifyService.sendSignUpVerifyEmail(address);

        if (!isSend) {
            throw new EmailSendFailureException("서버의 오류로 인해 전송에 실패했습니다.");
        }

        return ResponseEntity.ok(new RequestSuccessDto());
    }

    /**
     * 회원가입을 위한 이메일 주소인증을 승인하기 위한 컨트롤러입니다.
     *
     * @param verifyCode 회원에게 전송되었던 인증코드입니다.
     * @return 요청에 성공했다는 응답을 알리기위한 dto객체입니다.
     */
    @GetMapping("/email/{verifyCode}")
    public ResponseEntity<RequestSuccessDto> verifyEmail(@PathVariable String verifyCode) {
        boolean isVerified = verifyService.approveVerificationEmail(verifyCode);

        if (!isVerified) {
            throw new EmailVerificationImpossibleException();
        }

        return ResponseEntity.ok(new RequestSuccessDto());
    }
}
