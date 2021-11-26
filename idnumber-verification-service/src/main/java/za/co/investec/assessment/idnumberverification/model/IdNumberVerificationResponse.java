package za.co.investec.assessment.idnumberverification.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class IdNumberVerificationResponse {
    @JsonProperty
    private boolean valid;
}
