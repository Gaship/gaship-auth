package shop.gaship.gashipauth.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * packageName    : shop.gaship.gashipauth.util <br/>
 * fileName       : JwtTokenUtilTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
@SpringBootTest
class JwtTokenUtilTest {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    void createUtil() {
        SignInSuccessUserDetailsDto tokenDto = new SignInSuccessUserDetailsDto();
        tokenDto.setAuthorities(List.of("ROLE_ADMIN"));
        tokenDto.setEmail("examole@nhn.com");
        String accessToken = jwtTokenUtil.createAccessToken(tokenDto);

        assertThat(accessToken).isNotNull();
        System.out.println(accessToken);
    }

    @Test
    void createAccessToken() {
        SignInSuccessUserDetailsDto tokenDto = new SignInSuccessUserDetailsDto();
        tokenDto.setAuthorities(List.of("ROLE_ADMIN"));
        tokenDto.setEmail("examole@nhn.com");
        String accessToken = jwtTokenUtil.createAccessToken(tokenDto);

        assertThat(accessToken).isNotNull();
        System.out.println(accessToken);
    }

    @Test
    void createRefreshToken() {
    }
}
