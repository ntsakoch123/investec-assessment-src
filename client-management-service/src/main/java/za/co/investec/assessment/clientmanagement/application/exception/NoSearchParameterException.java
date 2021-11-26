package za.co.investec.assessment.clientmanagement.application.exception;

import org.springframework.http.HttpStatus;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

public class NoSearchParameterException extends ApiException{

    public NoSearchParameterException() {
        super("No search parameter was specified, please provide at least one search parameter.", ErrorType.MISSING_PARAMETER, HttpStatus.BAD_REQUEST);
    }
}
