package za.co.investec.assessment.clientmanagement.application.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import za.co.investec.assessment.clientmanagement.application.exception.NoSearchParameterException;

import java.util.Optional;

@Setter
@ToString
@NoArgsConstructor
public class SearchClientDTO {

    private String firstName;
    private String mobileNumber;
    private String idNumber;

    public SearchClientDTO(String firstName, String mobileNumber, String idNumber) {
        if (StringUtils.isAllBlank(firstName, mobileNumber, idNumber)) throw new NoSearchParameterException();
        this.firstName = firstName;
        this.mobileNumber = mobileNumber;
        this.idNumber = idNumber;

    }

    public Optional<String> getFirstName() {
        return StringUtils.isNotBlank(firstName) ? Optional.of(firstName) : Optional.empty();
    }

    public Optional<String> getMobileNumber() {
        return StringUtils.isNotBlank(mobileNumber) ? Optional.of(mobileNumber) : Optional.empty();
    }

    public Optional<String> getIdNumber() {
        return StringUtils.isNotBlank(idNumber) ? Optional.of(idNumber) : Optional.empty();
    }

}
