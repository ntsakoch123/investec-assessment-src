package za.co.investec.assessment.clientmanagement.application.exception;

import za.co.investec.assessment.clientmanagement.domain.model.IdNumber;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

@ResponseStatus(HttpStatus.CONFLICT)
public class ClientIdNumberExistsException extends ApiException {

    public ClientIdNumberExistsException(IdNumber idNumber) {
        super(StringUtils.join("Client with ID number: ", idNumber.getValue()
                ," already exists."), ErrorType.INPUT_REJECT, HttpStatus.CONFLICT);
    }
}
