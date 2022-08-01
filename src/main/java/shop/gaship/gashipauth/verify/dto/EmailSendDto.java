package shop.gaship.gashipauth.verify.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

/**
 * 이메일을 전송하기위한 틀의 성격을 가진 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
@Builder
public class EmailSendDto {
    private String templateId;
    private Map<String, String> templateParameter;
    private List<EmailReceiver> receiverList;
}
