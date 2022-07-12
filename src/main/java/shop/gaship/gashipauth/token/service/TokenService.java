package shop.gaship.gashipauth.token.service;

import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.util.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token.service <br/>
 * fileName       : TokenService <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성                         <br/>
 */
public interface TokenService {
    JwtTokenDto createToken(SignInSuccessUserDetailsDto userDetailsDto);
}
