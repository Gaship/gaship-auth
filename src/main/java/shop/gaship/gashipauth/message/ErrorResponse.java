package shop.gaship.gashipauth.message;

/**
 * packageName    : shop.gaship.gashipshoppingmall.message
 * fileName       : ErrorResponse
 * author         : 김민수
 * date           : 2022-07-14
 * description    : 에러나 예외 발생 시 응답하는 response body 타입
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-14        김민수       최초 생성
 */
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
