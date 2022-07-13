package shop.gaship.gashipauth.token.dummy;

import java.util.List;
import shop.gaship.gashipauth.util.SignInSuccessUserDetailsDto;

/**
 * packageName    : shop.gaship.gashipauth.token.dummy <br/>
 * fileName       : SignInSuccessUserDetailsDtoDummy <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public class SignInSuccessUserDetailsDtoDummy {
    private SignInSuccessUserDetailsDtoDummy(){ }

    public static SignInSuccessUserDetailsDto dummy(){
        SignInSuccessUserDetailsDto dummyUserDetails = new SignInSuccessUserDetailsDto();
        dummyUserDetails.setIdentifyNo(1L);
        dummyUserDetails.setEmail("exmaple@nhn.com");
        dummyUserDetails.setAuthorities(List.of("ROLE_MEMBER"));

        return dummyUserDetails;
    }
}
