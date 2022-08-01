package shop.gaship.gashipauth.verify.dto;

import java.util.List;
import lombok.Getter;

/**
 * 이메일을 성공적을 보냈을 시의 http body결과를 클래스로 표현한 것입니다.
 *
 * @author : 김민수
 * @since 1.0
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
