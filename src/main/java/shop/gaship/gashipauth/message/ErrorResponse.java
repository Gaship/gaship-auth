package shop.gaship.gashipauth.message;

/**
 * packageName    : shop.gaship.gashipshoppingmall.message<br/>
 * fileName       : ErrorResponse<br/>
 * author         : 김민수<br/>
 * date           : 2022-07-15<br/>
 * description    : 에러나 예외 발생 시 응답하는 response body 타입<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2022-07-15        김민수               최초 생성<br/>
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
