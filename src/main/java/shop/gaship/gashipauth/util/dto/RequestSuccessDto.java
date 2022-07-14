package shop.gaship.gashipauth.util.dto;

import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipauth.util.dto <br/>
 * fileName       : RequestSuccessDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : 사용자의 응답에 일반적인 성공시 주기위한 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성              <br/>
 */
@Getter
public class RequestSuccessDto {
    private final String requestStatus = "success";
}
