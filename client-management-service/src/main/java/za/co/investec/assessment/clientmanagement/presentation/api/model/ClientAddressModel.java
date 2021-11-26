package za.co.investec.assessment.clientmanagement.presentation.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ClientAddressModel  implements  RequestModel{

    @JsonProperty
    @NotEmpty(message = "{field.required}")
    @Size(min = 2,max = 100, message = "{address.streetName.size}")
    private String streetName;

    @NotNull(message = "{field.required}")
    @Min(value = 1, message = "{address.postalCode.minLength}")
    private Long houseNumber;

    @JsonProperty
    @NotEmpty(message = "{field.required}")
    @Size(min = 3,max = 100, message = "{address.suburb.size}")
    private String suburb;

    @JsonProperty
    @NotNull(message = "{field.required}")
    @Min(value = 1, message = "{address.postalCode.minLength}")
    @Max(value = 9999, message = "{address.postalCode.maxLength}")
    private Long postalCode;

    @JsonProperty(defaultValue = "false")
    private boolean removed;

}
