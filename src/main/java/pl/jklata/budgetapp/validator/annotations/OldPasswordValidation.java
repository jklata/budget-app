package pl.jklata.budgetapp.validator.annotations;

import pl.jklata.budgetapp.validator.OldPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = OldPasswordValidator.class)
@Documented

public @interface OldPasswordValidation {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String login();

    String oldPassword();

    String changePassword();


}
