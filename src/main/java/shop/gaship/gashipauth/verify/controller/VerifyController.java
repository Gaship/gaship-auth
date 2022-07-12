package shop.gaship.gashipauth.verify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.util.dto.RequestSuccessDto;

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
public class VerifyController {
    @GetMapping("/email")
    public RequestSuccessDto requestEmailVerify(){

        return new RequestSuccessDto();
    }
}
