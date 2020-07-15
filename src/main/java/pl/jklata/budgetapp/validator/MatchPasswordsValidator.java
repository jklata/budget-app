package pl.jklata.budgetapp.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import pl.jklata.budgetapp.validator.annotations.MatchPasswordsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
            String passwordFromInput = (String) PropertyUtils.getProperty(value, password);
            String retypedPasswordFromInput = (String) PropertyUtils.getProperty(value, retypedPassword);
            if (!passwordFromInput.equals(retypedPasswordFromInput)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(message).addPropertyNode(retypedPassword).addConstraintViolation();
            } else {
                isValid = true;
            }

        } catch (Exception e) {
            log.error("Can't access property in UserDto via MatchPasswordsValidator");
        }
        return isValid;
    }
}
