package za.co.investec.assessment.clientmanagement.presentation.api.model.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private ErrorType type;

    @JsonProperty("messages")
    private List<String> messages;

    @JsonProperty("type")
    public String getType() {
        return type.toString();
    }
}
