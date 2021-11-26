package za.co.investec.assessment.clientmanagement.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class ClientAddressDTO {
    private final long houseNumber;
    private final String streetName;
    private final String suburb;
    private final long postalCode;
    @Setter
    private boolean addressRemoved;

    public ClientAddressDTO(long houseNumber, String streetName, String suburb, long postalCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.suburb = suburb;
        this.postalCode = postalCode;
    }
}
