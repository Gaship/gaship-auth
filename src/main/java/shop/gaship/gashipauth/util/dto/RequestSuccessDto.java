package shop.gaship.gashipauth.util.dto;

import lombok.Getter;

/**
 * 사용자의 응답에 일반적인 성공시 주기위한 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
public class RequestSuccessDto {
    private final String requestStatus = "success";
}
