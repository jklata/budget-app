package pl.jklata.budgetapp.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import pl.jklata.budgetapp.validator.annotations.MatchUpdatePasswordsValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static pl.jklata.budgetapp.validator.utils.ValidatorHelper.matchesPasswords;

@Slf4j
public class MatchUpdatePasswordsValidator implements ConstraintValidator<MatchUpdatePasswordsValidation, Object> {

    private String newPassword;
    private String retypedPassword;
    private String message;
    private String changePassword;


    @Override
    public void initialize(MatchUpdatePasswordsValidation constraintAnnotation) {
        this.newPassword = constraintAnnotation.newPassword();
        this.retypedPassword = constraintAnnotation.retypedPassword();
        this.message = constraintAnnotation.message();
        this.changePassword = constraintAnnotation.changePassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean isValid = false;
        try {
            boolean changePasswordInput = (boolean) PropertyUtils.getProperty(value, changePassword);
            if (!changePasswordInput) {
                return true;
            }
            isValid = matchesPasswords(value, context, newPassword, retypedPassword, message);

        } catch (Exception e) {
            log.error("Can't access property in UserUpdateDto via MatchUpdatePasswordsValidator");
        }
        return isValid;
    }

}
