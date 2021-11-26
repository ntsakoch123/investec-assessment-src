package za.co.investec.assessment.clientmanagement.presentation.api.exceptionhandling;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import za.co.investec.assessment.clientmanagement.application.exception.ApiException;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorType;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ErrorResponse;
import za.co.investec.assessment.clientmanagement.presentation.api.model.error.ResponseErrorWrapper;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseErrorWrapper> runtimeExceptionHandler(Exception ex, WebRequest request) {
        if (ex.getCause() != null) {
            if (ApiException.class.isAssignableFrom(ex.getCause().getClass())) {
                return handleApplicationException((ApiException) ex.getCause(), request);
            }
        }
        log.error("Exception: ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessages(Collections.singletonList("Server error occurred."));
        errorResponse.setType(ErrorType.SERVER_ERROR);
        ResponseErrorWrapper errorWrapper = new ResponseErrorWrapper(errorResponse);
        return new ResponseEntity<>(errorWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ResponseErrorWrapper> handleApplicationException(ApiException ex, WebRequest request) {
        log.error("ApiException: ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessages(Collections.singletonList(ex.getMessage()));
        errorResponse.setType(ex.getErrorType());
        ResponseErrorWrapper errorWrapper = new ResponseErrorWrapper(errorResponse);
        return new ResponseEntity<>(errorWrapper, ex.getHttpStatus());
    }

    @SneakyThrows
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException: ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getFieldErrors()) {
            String rejectedValue = error.getRejectedValue() != null ? error.getRejectedValue().toString() : "null";
            String message = StringUtils.join(error.getField()," ", error.getDefaultMessage(), " but was: ", rejectedValue);
            errors.add(message);
        }
        if (!errors.isEmpty()) {
            errorResponse.setMessages(errors);
        } else {
            List<String> messages = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            errorResponse.setMessages(messages);
        }
        errorResponse.setType(ErrorType.VALIDATION_FAILURES);
        ResponseErrorWrapper errorWrapper = new ResponseErrorWrapper(errorResponse);
        return new ResponseEntity<>(errorWrapper, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MissingServletRequestParameterException: ", ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessages(Collections.singletonList(Objects.toString(ex)));
        errorResponse.setType(ErrorType.MISSING_PARAMETER);
        ResponseErrorWrapper errorWrapper = new ResponseErrorWrapper(errorResponse);
        return new ResponseEntity<>(errorWrapper, HttpStatus.BAD_REQUEST);
    }

}
