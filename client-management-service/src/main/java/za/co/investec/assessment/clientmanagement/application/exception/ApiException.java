package za.co.investec.assessment.clientmanagement.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;

@Getter
public class ApiException extends RuntimeException{

    private final ErrorType errorType;
    private final HttpStatus httpStatus;

    public ApiException(String message, ErrorType errorType, HttpStatus httpStatus){
        super(message);
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }
}
