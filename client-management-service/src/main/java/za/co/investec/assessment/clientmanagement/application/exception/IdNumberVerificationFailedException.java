package za.co.investec.assessment.clientmanagement.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNumberVerificationFailedException extends ApiException{
    public IdNumberVerificationFailedException() {
        super("ID Number verification failed", ErrorType.INPUT_REJECT, HttpStatus.BAD_REQUEST);
    }
}
