package pl.jklata.budgetapp.validator;

import lombok.extern.slf4j.Slf4j;
import pl.jklata.budgetapp.validator.annotations.MatchPasswordsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static pl.jklata.budgetapp.validator.utils.ValidatorHelper.matchesPasswords;

@Slf4j
public class MatchPasswordsValidator implements ConstraintValidator<MatchPasswordsValidation, Object> {

    private String password;
    private String retypedPassword;
    private String message;

    @Override
    public void initialize(MatchPasswordsValidation constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.retypedPassword = constraintAnnotation.retypedPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            isValid = matchesPasswords(value, context, password, retypedPassword, message);
        } catch (Exception e) {
            log.error("Can't access property in UserDto via MatchPasswordsValidator");
        }
        return isValid;
    }
}
