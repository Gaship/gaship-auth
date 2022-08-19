package shop.gaship.gashipauth.auth.controller;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.auth.service.AuthService;
import shop.gaship.gashipauth.token.dto.request.UserInfoForJwtRequestDto;
import shop.gaship.gashipauth.token.dto.response.JwtResponseDto;

/**
 * Auth 관련 Controller 클래스.
 *
 * @author : 조재철
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping(value = "/securities")
@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * /logout 요청을 받아서 로그아웃 관련 응답을 하는 메서드.
     *
     * @param memberNo member의 number.
     * @param request  HTTP 서블릿에 대한 요청 정보.
     * @return logout이 되었는지 응답 반환.
     */
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(@RequestBody Integer memberNo, HttpServletRequest request) {
        String accessToken = request.getHeader("X-AUTH-ACCESS-TOKEN");
        String refreshToken = request.getHeader("X-AUTH-REFRESH-TOKEN");
        authService.logout(accessToken, refreshToken, memberNo);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * /issue-token 요청을 받아서 jwt 토큰을 반환하는 클래스.
     *
     * @param userInfoDto 회원 정보(회원 번호, 권한).
     * @return 토큰이 재발급 되었는지 응답 반환.
     */
    @PostMapping(value = "/issue-token")
    public ResponseEntity<JwtResponseDto> issueJwt(@RequestBody UserInfoForJwtRequestDto userInfoDto) {
        return ResponseEntity.ok(authService.issueJwt(userInfoDto));
    }
}
