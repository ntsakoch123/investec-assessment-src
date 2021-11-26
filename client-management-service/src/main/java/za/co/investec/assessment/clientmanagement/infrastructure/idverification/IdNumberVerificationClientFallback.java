package za.co.investec.assessment.clientmanagement.infrastructure.idverification;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class IdNumberVerificationClientFallback implements IdNumberVerificationClient {

    private Exception apiInvocationException;

    public IdNumberVerificationClientFallback(Exception exception) {
        this.apiInvocationException = exception;
    }

    @Override
    public IdNumberVerificationResponse verify(String idNumber) {
        log.error("apiInvocationException..using default fall back client: ", apiInvocationException);
        return new IdNumberVerificationResponse(true);
    }
}
