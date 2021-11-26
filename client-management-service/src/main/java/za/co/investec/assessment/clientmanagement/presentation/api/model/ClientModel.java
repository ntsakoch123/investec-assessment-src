package za.co.investec.assessment.clientmanagement.presentation.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import za.co.investec.assessment.clientmanagement.presentation.api.validation.IdNumber;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName("ClientModel")
public class ClientModel  implements RequestModel{

    @JsonProperty
    private Long clientId;

    @JsonProperty
    @NotEmpty(message = "{field.required}")
    @IdNumber(message = "{invalidIdNumber}")
    private String idNumber;

    @JsonProperty
    @NotEmpty(message = "{field.required}")
    @Size(min = 2,max = 100, message = "{client.names.size}")
    private String firstName;

    @JsonProperty
    @NotEmpty(message = "{field.required}")
    @Size(min = 2,max = 100, message = "{client.names.size}")
    private String lastName;

    @JsonProperty
    @Pattern(regexp = "^(0\\d{9})?$", message = "{mobileNumber.size}")
    private String mobileNumber;

    @Valid
    @Getter(AccessLevel.NONE)
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ClientAddressModel physicalAddress;

    public Optional<ClientAddressModel> getPhysicalAddress(){
        return Objects.nonNull(physicalAddress) ? Optional.of(physicalAddress) : Optional.empty();
    }
}
