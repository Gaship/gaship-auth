package shop.gaship.gashipauth.auth.dto.request;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * jwt 토큰을 요청한뒤 응답받을때 사용하기위한 dto 입니다.
 *
 * @author 조재철
 * @since 1.0
 */
@Getter
@Setter
public class ReissueJwtRequestDto implements Serializable {

    @NotBlank(message = "refresh token 값이 필요합니다.")
    private String refreshToken;

    @Min(value = 0, message = "memberNo 값이 필요합니다.")
    private Integer memberNo;

    private List<String> authorities;

}
