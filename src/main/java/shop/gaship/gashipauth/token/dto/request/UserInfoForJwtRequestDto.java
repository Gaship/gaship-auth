package shop.gaship.gashipauth.token.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Jwt payload에 사용될 정보들을 전달하는 dto 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Getter
@Setter
public class UserInfoForJwtRequestDto {

    private Integer memberNo;
    private List<String> authorities;
}
