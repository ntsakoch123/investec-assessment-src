package za.co.investec.assessment.clientmanagement.infrastructure.idverification;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import za.co.investec.assessment.clientmanagement.utils.ApiProperties;

@FeignClient(name = "idnumber-verification-service")
public interface IdNumberVerificationClient {
    String SERVICE_NAME = "idnumber-verification-service";

    @CircuitBreaker(name = SERVICE_NAME)
    @PostMapping(ApiProperties.URLS.ID_NUMBER_VALIDATION_URL)
    IdNumberVerificationResponse verify(@RequestParam("idNumber") String idNumber);

}
