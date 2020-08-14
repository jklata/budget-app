package pl.jklata.budgetapp.validator.utils;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class ValidatorHelper {

    public static boolean matchesPasswords(Object value, ConstraintValidatorContext context, String password,
                                           String retypedPassword, String message) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String passwordFromInput = (String) PropertyUtils.getProperty(value, password);
        String retypedPasswordFromInput = (String) PropertyUtils.getProperty(value, retypedPassword);
        if (!passwordFromInput.equals(retypedPasswordFromInput)) {
            buildErrorMessage(context, message, retypedPassword);
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(retypedPassword).addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }

    public static void buildErrorMessage(ConstraintValidatorContext context, String errorMessage, String errorField) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage).addPropertyNode(errorField).addConstraintViolation();
    }


}
