package shop.gaship.gashipauth.util;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @Min(0)
    @NotNull(message = "식별번호는 반드시 있어야합니다.")
    private Long identifyNo;

    @Email
    @NotBlank(message = "이메일이 비어있습니다.")
    private String email;

    @NotEmpty(message = "권한은 반드시 1가지 이상 존재해야합니다.")
    private List<@Valid String> authorities;
}
