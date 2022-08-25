package shop.gaship.gashipauth.verify.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * 이메일을 받을 사람의 정보를 입력하는 클래스입니다.
 *
 * @author : 김민수
 * @since 1.0
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
