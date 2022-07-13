package shop.gaship.gashipauth.auth.service;

import org.springframework.http.ResponseEntity;

/**
 * packageName    : shop.gaship.gashipauth.token.service fileName       : AuthService author
 * : jo date           : 2022/07/12 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2022/07/12        jo       최초 생성
 */
public interface AuthService {

    ResponseEntity<?> logout(String token);
}
