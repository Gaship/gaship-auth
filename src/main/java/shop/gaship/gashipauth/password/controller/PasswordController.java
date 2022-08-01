package shop.gaship.gashipauth.password.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipauth.password.dto.request.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipauth.password.dto.response.SuccessReissueResponse;
import shop.gaship.gashipauth.password.service.PasswordService;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequestMapping("/securities/password")
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/reissue")
    public ResponseEntity<SuccessReissueResponse> passwordReissueRequest(
        @Valid ReissuePasswordReceiveEmailDto passwordReceiveEmailDto) {
        return ResponseEntity.ok(
            passwordService.sendReissuePasswordByEmail(passwordReceiveEmailDto));
    }
}
