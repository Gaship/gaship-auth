package shop.gaship.gashipauth.exceptions;

/**
 * packageName    : shop.gaship.gashipauth.exceptions<br/>
 * fileName       : NoResponseDataException<br/>
 * author         : oct_sky_out<br/>
 * date           : 2022/07/09<br/>
 * description    : 응답 데이터가 존재하지않을 경우의 예외<br/>
 * ===========================================================<br/>
 * DATE              AUTHOR             NOTE<br/>
 * -----------------------------------------------------------<br/>
 * 2022/07/09        oct_sky_out       최초 생성<br/>
 */
public class NoResponseDataException extends RuntimeException {
    public NoResponseDataException(String message) {
        super(message);
    }
}
