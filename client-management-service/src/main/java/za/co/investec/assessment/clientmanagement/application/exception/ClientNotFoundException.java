package za.co.investec.assessment.clientmanagement.application.exception;

import za.co.investec.assessment.clientmanagement.domain.model.ClientId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends ApiException {

    public ClientNotFoundException(ClientId clientId) {
        super(StringUtils.join("Client with ID: ",clientId.getValue(),
                " not found."), ErrorType.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
