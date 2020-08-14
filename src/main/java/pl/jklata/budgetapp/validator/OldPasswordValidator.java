package pl.jklata.budgetapp.validator;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.validator.annotations.OldPasswordValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static pl.jklata.budgetapp.validator.utils.ValidatorHelper.buildErrorMessage;

@Slf4j
public class OldPasswordValidator implements ConstraintValidator<OldPasswordValidation, Object> {

    private UserRepository userRepository;
    private String login;
    private String oldPassword;
    private PasswordEncoder passwordEncoder;
    private String message;
    private String changePassword;


    @Autowired
    public OldPasswordValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initialize(OldPasswordValidation constraintAnnotation) {
        this.login = constraintAnnotation.login();
        this.oldPassword = constraintAnnotation.oldPassword();
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
            String loginInput = (String) PropertyUtils.getProperty(value, "login");

            String oldPasswordInput = (String) PropertyUtils.getProperty(value, "oldPassword");
            String oldPasswordFromDB = userRepository.findByLogin(loginInput).getPassword();
            boolean isPasswordsMatches = passwordEncoder.matches(oldPasswordInput, oldPasswordFromDB);
            if (!isPasswordsMatches) {
                buildErrorMessage(context, message, oldPassword);
            } else {
                isValid = true;
            }

        } catch (Exception e) {
            log.error("Can't access property in UserUpdateDto via MatchUpdatePasswordsValidator");
        }
        return isValid;
    }

}
