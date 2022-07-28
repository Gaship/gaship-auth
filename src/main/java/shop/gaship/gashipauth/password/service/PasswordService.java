package shop.gaship.gashipauth.password.service;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import shop.gaship.gashipauth.password.dto.request.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipauth.password.dto.response.SuccessReissueResponse;
import shop.gaship.gashipauth.util.EmailSenderUtil;
import shop.gaship.gashipauth.verify.dto.EmailReceiver;
import shop.gaship.gashipauth.verify.dto.EmailSendDto;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class PasswordService {
    private static final String TEMPLATE_ID = "reissuePasswordTemplate";
    private final EmailSenderUtil emailSenderUtil;

    public SuccessReissueResponse sendReissuePasswordByEmail(
        ReissuePasswordReceiveEmailDto receiveEmail) {
        String reissuePassword = RandomStringUtils.random(5, 33, 64, false, false)
            + RandomStringUtils.randomAlphanumeric(15);
        EmailReceiver receiver = new EmailReceiver(receiveEmail.getEmail(), null, "MRT0");

        EmailSendDto emailSendDto = EmailSendDto.builder()
            .templateId(TEMPLATE_ID)
            .templateParameter(Map.of("reissue", reissuePassword))
            .receiverList(List.of(receiver))
            .build();

        emailSenderUtil.sendMail(emailSendDto);

        return new SuccessReissueResponse(receiveEmail.getEmail(), reissuePassword);
    }
}
