package za.co.investec.assessment.clientmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import za.co.investec.assessment.clientmanagement.infrastructure.idverification.IdNumberVerificationClient;
import za.co.investec.assessment.clientmanagement.presentation.api.validation.IdNumberValidator;
import za.co.investec.assessment.clientmanagement.infrastructure.idverification.IdNumberVerificationResponse;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfig {

    /**
     *  Override default bean to prevent calling external service
     *
     */
    @Bean
    @Primary
    public IdNumberVerificationClient idNumberVerificationClient(){
        return idNumber -> new IdNumberVerificationResponse(new IdNumberValidator().isValid(idNumber, null));
    }
}
