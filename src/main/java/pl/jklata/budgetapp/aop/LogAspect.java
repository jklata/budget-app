package pl.jklata.budgetapp.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.jklata.budgetapp.service.EventLogService;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private EventLogService eventLogService;

    @Autowired
    public LogAspect(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @Around("@annotation(LogEvent)")
    public Object logAdvice(ProceedingJoinPoint jp) throws Throwable {
        log.info("Start AOP execution of event log service...");

        List<Object> annotatedMethodParameters = retrieveIncomingObjectList(jp);
        eventLogService.preserveBeforeState(annotatedMethodParameters);
        Object obj = jp.proceed();
        eventLogService.preserveAfterState(obj);
        eventLogService.processEventLog();
        return obj;
    }

    private List<Object> retrieveIncomingObjectList(ProceedingJoinPoint jp) {
        Object[] args = jp.getArgs();
        return Arrays.asList(args);
    }
}