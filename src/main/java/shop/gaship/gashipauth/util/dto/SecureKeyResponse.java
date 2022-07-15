package shop.gaship.gashipauth.util.dto;

import lombok.Getter;

/**
 *
 * nhn secure key manager의 body 타입입니다.
 *
 * @author : 김민수
 * @since 1.0
 */
@Getter
public class SecureKeyResponse {
    private Header header;
    private Body body;


    @Getter
    public static class Header {
        private Integer resultCode;
        private String resultMessage;
        private Boolean isSuccessful;
    }

    @Getter
    public static class Body {
        private String secret;
    }
}
