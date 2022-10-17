package pl.jklata.budgetapp.controller.handler;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;
import pl.jklata.budgetapp.dto.ErrorDto;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {


    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleValidationException(final NoHandlerFoundException exception,
        final HttpServletRequest request) {

        String message = prepareExceptionMessage(exception, request);
        log.error(message);
        return "errors/error-500";
    }

    @ExceptionHandler(TemplateInputException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleValidationException3(final TemplateInputException exception,
        final HttpServletRequest request) {

        String message = prepareExceptionMessage(exception, request);
        log.error(message);
        return "errors/error-500";
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleValidationException2(final Exception exception,
        final HttpServletRequest request) {

        String message = prepareExceptionMessage(exception, request);
        log.error(message);
        return "errors/error-500";
    }

    private String prepareExceptionMessage(Exception exception, HttpServletRequest request) {
        return String
            .format("%s when invoking: %s; root cause: %s", exception, request.getRequestURI(),
                ExceptionUtils.getRootCauseMessage(exception));
    }


}
