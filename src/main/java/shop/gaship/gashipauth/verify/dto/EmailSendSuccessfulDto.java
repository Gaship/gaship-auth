package shop.gaship.gashipauth.verify.dto;

import java.util.List;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipauth.verify.dto <br/>
 * fileName       : EmailSendSuccessfulDto <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : 이메일을 성공적을 보냈을 시의 http body결과를 클래스로 표현한 것입니다.<br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@Getter
public class EmailSendSuccessfulDto {
    Header header;
    Body body;

    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    @Getter
    public static class Body {
        private Data data;

        @Getter
        public static class Data{
            private String requestId;
            private List<EmailSendResult> results;

            @Getter
            public static class EmailSendResult{
                private String receiveMailAddr;
                private String receiveName;
                private String receiveType;
                private Integer resultCode;
                private String resultMessage;
            }
        }
    }
}
