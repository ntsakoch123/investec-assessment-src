package za.co.investec.assessment.clientmanagement.application.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.investec.assessment.clientmanagement.domain.model.MobileNumber;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClientMobileNumberAlreadyExistsException extends ApiException {
    public ClientMobileNumberAlreadyExistsException(MobileNumber mobileNumber) {
        super(StringUtils.join("Client with mobile number: ",
                mobileNumber.getValue() ," already exists."), ErrorType.INPUT_REJECT, HttpStatus.CONFLICT);
    }
}
