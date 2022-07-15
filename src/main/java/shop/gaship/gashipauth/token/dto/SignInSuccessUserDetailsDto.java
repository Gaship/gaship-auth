package shop.gaship.gashipauth.token.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * 로그인 성공 후 토큰의 payload에 들어갈 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
@Setter
public class SignInSuccessUserDetailsDto{
    private Long identifyNo;
    private List<String> authorities;
}
