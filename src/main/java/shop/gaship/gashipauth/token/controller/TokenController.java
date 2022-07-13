package shop.gaship.gashipauth.token.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;
import shop.gaship.gashipauth.token.service.TokenService;
import shop.gaship.gashipauth.util.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token <br/>
 * fileName       : TokenController <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/11 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/11           김민수               최초 생성              <br/>
 */
@RestController
@RequestMapping("/securities/issue-token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping
    public JwtTokenDto publishToken(
        @RequestBody @Validated SignInSuccessUserDetailsDto signInSuccessUserDetailsDto) {
        return tokenService.createToken(signInSuccessUserDetailsDto);
    }
}
