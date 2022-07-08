package shop.gaship.gashipauth.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipauth.util
 * fileName       : JsonPlaceHolderResult
 * author         : oct_sky_out
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        oct_sky_out       최초 생성
 */
@NoArgsConstructor
@Data
public class JsonPlaceHolderResult {
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;


}
