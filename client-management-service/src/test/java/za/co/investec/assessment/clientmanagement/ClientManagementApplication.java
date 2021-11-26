package za.co.investec.assessment.clientmanagement;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Primary;

/**
 * Override main to disable some features during integration tests
 */
@Primary
@SpringBootApplication
public class ClientManagementApplication {

}