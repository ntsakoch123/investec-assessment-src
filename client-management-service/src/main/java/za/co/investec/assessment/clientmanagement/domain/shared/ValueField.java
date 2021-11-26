package za.co.investec.assessment.clientmanagement.domain.shared;

import lombok.NoArgsConstructor;
import java.util.Objects;

@NoArgsConstructor
public abstract class ValueField<V> implements ValueObject<V>{

    public abstract V getValue();

    public boolean sameValueAs(V other) {
        return Objects.equals(getValue(), other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public boolean equals(Object other) {
        try {
            if (this == other) return true;
            return sameValueAs((V) other);
        } catch (ClassCastException exc) {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
