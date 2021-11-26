package za.co.investec.assessment.clientmanagement.infrastructure.idverification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class IdNumberVerificationResponse {
    private boolean valid;

    public IdNumberVerificationResponse(boolean valid) {
        this.valid = valid;
    }
}
