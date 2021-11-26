package za.co.investec.assessment.clientmanagement.presentation.api.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@JsonRootName("ResponseErrorWrapper")
public class ResponseErrorWrapper {

    @JsonProperty("error")
    private final ErrorResponse errorResponse;
}
