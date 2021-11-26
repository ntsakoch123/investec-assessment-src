package za.co.investec.assessment.clientmanagement.presentation.api.model.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorType {
    MISSING_PARAMETER("MissingRequiredParameter"),
    INPUT_REJECT("InputRejected"),
    VALIDATION_FAILURES("InputValidationFailures"),
    RESOURCE_NOT_FOUND("ResourceNotFound"),
    SERVER_ERROR("InternalServerError");

    private final String description;

    public String toString() {
        return description;
    }
}
