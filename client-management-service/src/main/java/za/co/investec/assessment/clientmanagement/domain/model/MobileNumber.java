package za.co.investec.assessment.clientmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import za.co.investec.assessment.clientmanagement.domain.shared.ValueField;
import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MobileNumber extends ValueField<String> {

    @Column(name = "mobile_number")
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }
}
