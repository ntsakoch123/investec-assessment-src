package za.co.investec.assessment.clientmanagement.application.dto;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Getter
@ToString
@AllArgsConstructor
public class ClientDetailsDTO {

    private final String firstName;
    private final String lastName;
    private final String idNumber;

    @Setter
    @Getter(AccessLevel.NONE)
    private String mobileNumber;

    @Setter
    @Getter(AccessLevel.NONE)
    private ClientAddressDTO clientAddressDTO;

    public ClientDetailsDTO(String firstName, String lastName, String idNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idNumber = idNumber;
    }

    public Optional<ClientAddressDTO> getClientAddressDTO(){
        return Objects.nonNull(clientAddressDTO) ? Optional.of(clientAddressDTO) : Optional.empty();
    }

    public Optional<String> getMobileNumber(){
        return StringUtils.isNotBlank(mobileNumber)? Optional.of(mobileNumber) : Optional.empty();
    }
}
