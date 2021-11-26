package za.co.investec.assessment.clientmanagement.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import za.co.investec.assessment.clientmanagement.domain.shared.ValueField;

@Getter
@AllArgsConstructor
public class ClientId extends ValueField<Long> {

    private final Long value;

    @Override
    public Long getValue() {
        return this.value;
    }
}
