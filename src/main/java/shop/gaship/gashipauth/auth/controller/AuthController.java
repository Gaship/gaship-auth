package shop.gaship.gashipauth.auth.controller;

import java.net.http.HttpResponse;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.dto.JwtTokenDto;

/**
 * packageName    : shop.gaship.gashipauth.auth.controller fileName       : AuthController author
 *      : jo date           : 2022/07/12 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2022/07/12        jo       최초 생성
 */

@RequiredArgsConstructor
@RequestMapping(value = "/securities")
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody JwtTokenDto jwtTokenDto) {
        log.info("hahah");
        return authService.logout(jwtTokenDto.getAccessToken());
    }
}
