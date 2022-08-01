package shop.gaship.gashipauth.password.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class SuccessReissueResponse {
    private String email;
    private String reissuedPassword;
}
