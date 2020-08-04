package pl.jklata.budgetapp.validator;

import lombok.extern.slf4j.Slf4j;
import pl.jklata.budgetapp.validator.annotations.DateNotInFutureValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Slf4j
public class DateNotInFutureValidator implements ConstraintValidator<DateNotInFutureValidation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate insertDate = ((LocalDate) value);
        LocalDate now = LocalDate.now();

        return insertDate.isBefore(now) || insertDate.isEqual(now);
    }
}
