package za.co.investec.assessment.clientmanagement.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.investec.assessment.clientmanagement.application.exception.IdNumberVerificationFailedException;
import za.co.investec.assessment.clientmanagement.domain.model.IdNumber;
import za.co.investec.assessment.clientmanagement.infrastructure.idverification.IdNumberVerificationClient;
import za.co.investec.assessment.clientmanagement.infrastructure.idverification.IdNumberVerificationResponse;

@Slf4j
@Service
public class IdNumberVerificationService {

    private final IdNumberVerificationClient idNumberVerificationClient;

    @Autowired
    public IdNumberVerificationService(IdNumberVerificationClient idNumberVerificationClient) {
        this.idNumberVerificationClient = idNumberVerificationClient;
    }

    public void verifyIdNumber(IdNumber idNumber) {
        IdNumberVerificationResponse verifyResponse = idNumberVerificationClient.verify(idNumber.getValue());
        if (!verifyResponse.isValid())
            throw new IdNumberVerificationFailedException();
    }

}
