package shop.gaship.gashipauth.util;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipauth.signin.dto
 * fileName       : SignInDto
 * author         : oct_sky_out
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        oct_sky_out       최초 생성
 */
@Getter
@Setter
public class SignInSuccessUserDetailsDto{
    private Long identifyNo;
    private String email;
    private List<String> authorities;
}
