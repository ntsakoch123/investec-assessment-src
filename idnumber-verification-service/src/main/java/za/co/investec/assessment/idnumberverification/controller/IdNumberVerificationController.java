package za.co.investec.assessment.idnumberverification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.investec.assessment.idnumberverification.model.IdNumberVerificationResponse;
import za.co.investec.assessment.idnumberverification.service.IdNumberVerificationService;

@RestController
public class IdNumberVerificationController {

    private final IdNumberVerificationService idNumberVerificationService;

    public IdNumberVerificationController(IdNumberVerificationService idNumberVerificationService) {
        this.idNumberVerificationService = idNumberVerificationService;
    }

    @PostMapping("/id-number/verify")
    public ResponseEntity<IdNumberVerificationResponse> validateIdNumber(@RequestParam String idNumber){
        IdNumberVerificationResponse idNumberVerificationResponse = idNumberVerificationService.validateIdNumber(idNumber);
        return ResponseEntity.ok(idNumberVerificationResponse);
    }
}
