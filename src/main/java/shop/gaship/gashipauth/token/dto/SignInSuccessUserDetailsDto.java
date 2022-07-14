package shop.gaship.gashipauth.token.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipauth.signin.dto<br/>
 * fileName       : SignInDto<br/>
 * author         : oct_sky_out<br/>
 * date           : 2022/07/09<br/>
 * description    : 로그인 성공 후 토큰의 payload에 들어갈 클래스입니다.<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2022/07/09        oct_sky_out       최초 생성
 */
@Getter
@Setter
public class SignInSuccessUserDetailsDto{
    private Long identifyNo;
    private List<String> authorities;
}
