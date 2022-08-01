package shop.gaship.gashipauth.password.dto.request;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReissuePasswordReceiveEmailDto {
    @Email
    private String email;
}
