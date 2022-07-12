package shop.gaship.gashipauth.util.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipauth.util.dto <br/>
 * fileName       : RequestSuccessDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Getter
@NoArgsConstructor
public class RequestSuccessDto {
    private final String requestStatus = "success";
}
