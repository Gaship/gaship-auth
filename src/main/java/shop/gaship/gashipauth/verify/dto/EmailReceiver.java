package shop.gaship.gashipauth.verify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * packageName    : shop.gaship.gashipauth.verify.dto <br/>
 * fileName       : EmailReceiverDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : 이메일을 받을 사람의 정보를 입력하는 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailReceiver {
    private String receiveMailAddr;
    @Nullable
    private String receiveName;
    private String receiveType;
}
