package shop.gaship.gashipauth.token.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * 생성한 jwt와 해당 만료시간을 담아서 전달하는 dto 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@Data
public class JwtResponseDto {

    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpireDateTime;
    private LocalDateTime refreshTokenExpireDateTime;
}
