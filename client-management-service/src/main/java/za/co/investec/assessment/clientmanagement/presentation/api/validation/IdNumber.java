package za.co.investec.assessment.clientmanagement.presentation.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdNumber {

    String message() default "not a valid South African ID Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
