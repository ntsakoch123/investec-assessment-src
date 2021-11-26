package za.co.investec.assessment.clientmanagement.domain.model;

import lombok.*;

import za.co.investec.assessment.clientmanagement.domain.shared.ValueField;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FirstName extends ValueField<String> {

    @Column(name = "first_name")
    private String value;

    @Override
    public String getValue() {
        return this.value;
    }

}
