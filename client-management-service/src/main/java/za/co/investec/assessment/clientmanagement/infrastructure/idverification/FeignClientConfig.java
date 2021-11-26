package za.co.investec.assessment.clientmanagement.infrastructure.idverification;

import feign.Feign;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FeignClientConfig {

    private final CircuitBreakerRegistry registry = CircuitBreakerRegistry.ofDefaults();

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        CircuitBreaker circuitBreaker = registry.circuitBreaker(IdNumberVerificationClient.SERVICE_NAME);
        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withFallbackFactory(IdNumberVerificationClientFallback::new)
                .build();
        return Resilience4jFeign.builder(decorators);
    }

}
