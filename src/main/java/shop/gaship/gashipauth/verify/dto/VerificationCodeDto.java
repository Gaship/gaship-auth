package shop.gaship.gashipauth.verify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 겜증코드를 담은 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class VerificationCodeDto {
    private String verificationCode;
}
