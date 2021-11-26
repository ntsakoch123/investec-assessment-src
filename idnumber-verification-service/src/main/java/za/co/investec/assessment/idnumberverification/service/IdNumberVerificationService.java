package za.co.investec.assessment.idnumberverification.service;

import org.springframework.stereotype.Service;
import za.co.investec.assessment.idnumberverification.integration.IdNumberVerificationClient;
import za.co.investec.assessment.idnumberverification.model.IdNumberVerificationResponse;

@Service
public class IdNumberVerificationService {

    private final IdNumberVerificationClient idNumberVerificationClient;

    public IdNumberVerificationService(IdNumberVerificationClient idNumberVerificationClient) {
        this.idNumberVerificationClient = idNumberVerificationClient;
    }

    public IdNumberVerificationResponse validateIdNumber(String idNumber){
        return idNumberVerificationClient.validate(idNumber);
    }
}
