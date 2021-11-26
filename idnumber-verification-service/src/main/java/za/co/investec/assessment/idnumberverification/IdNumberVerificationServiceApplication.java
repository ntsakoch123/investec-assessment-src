package za.co.investec.assessment.idnumberverification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class IdNumberVerificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdNumberVerificationServiceApplication.class, args);
    }

}
