package pl.jklata.budgetapp.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.jklata.budgetapp.dto.UserDto;
import pl.jklata.budgetapp.validator.utils.ValidatorHelper;

class ValidatorsTest {

    private DateNotInFutureValidator dateNotInFutureValidator;
    private MatchPasswordsValidator matchPasswordsValidator;
    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private PropertyUtils propertyUtils;

    @BeforeEach
    void setup() {

        dateNotInFutureValidator = new DateNotInFutureValidator();
        matchPasswordsValidator = new MatchPasswordsValidator();
    }

    @Test
    void date_not_in_the_future_validate_true() {
        //given
        Object value = LocalDate.of(1992, 5, 20);

        boolean result = dateNotInFutureValidator.isValid(value, context);

        assertTrue(result);
    }

    @Test
    void date_in_the_future_validate_false() {
        //given
        LocalDate now = LocalDate.now();
        Object value = now.plusMonths(5);

        boolean result = dateNotInFutureValidator.isValid(value, context);

        assertFalse(result);
    }

    @Test
    void match_password_validator_validate_true()
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserDto userDto = UserDto.builder()
            .password("password")
            .retypedPassword("password")
            .build();

        boolean result = ValidatorHelper
            .matchesPasswords(userDto, context, "password", "retypedPassword",
                "Podane hasła nie są jednakowe.");
        assertTrue(result);

    }

    @Test
    @Disabled
        //todo: null pointer na context
    void match_password_validator_validate_false()
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        UserDto userDto = UserDto.builder()
            .password("password")
            .retypedPassword("different password")
            .build();

        boolean result = ValidatorHelper
            .matchesPasswords(userDto, context, "password", "retypedPassword",
                "Podane hasła nie są jednakowe.");
        assertFalse(result);
    }
}
