package shop.gaship.gashipauth.auth.dto;

import lombok.Getter;

/**
 * @author : 조재철
 * @since 1.0
 */
@Getter
public class SecureKeyResponseDto {

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
