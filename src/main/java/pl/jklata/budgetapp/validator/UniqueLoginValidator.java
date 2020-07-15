package pl.jklata.budgetapp.validator;

import org.springframework.beans.factory.annotation.Autowired;
import pl.jklata.budgetapp.repository.UserRepository;
import pl.jklata.budgetapp.validator.annotations.UniqueLoginValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLoginValidation, Object> {

    private UserRepository userRepository;

    @Autowired
    public UniqueLoginValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String login = (String) value;
        if (userRepository.findByLogin(login) == null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void initialize(UniqueLoginValidation constraintAnnotation) {

    }
}
