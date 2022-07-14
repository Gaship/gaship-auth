package shop.gaship.gashipauth.verify.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipauth.verify.dto <br/>
 * fileName       : EmailSendDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : 이메일을 전송하기위한 틀의 성격을 가진 클래스입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Getter
@Builder
public class EmailSendDto {
    private String templateId;
    private Map<String, String> templateParameter;
    private List<EmailReceiver> receiverList;
}
