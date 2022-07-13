package shop.gaship.gashipauth.token.dummy;

import shop.gaship.gashipauth.token.dto.JwtTokenDto;

/**
 * packageName    : shop.gaship.gashipauth.token.dummy <br/>
 * fileName       : JwtTokenDtoDummy <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public class JwtTokenDtoDummy {
    private JwtTokenDtoDummy() {
    }

    public static JwtTokenDto dummy(){
        JwtTokenDto token = new JwtTokenDto();
        String accessToken = "examAccessToken1234";
        String refreshToken = "examRefreshToken1234";
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        return token;
    }
}
