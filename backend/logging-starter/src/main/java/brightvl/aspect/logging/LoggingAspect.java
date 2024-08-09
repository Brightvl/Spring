package brightvl.aspect.logging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProperties properties;

    @Pointcut("@annotation(brightvl.aspect.logging.Logging)") // method
    public void loggingMethodsPointcut() {
    }

    @Pointcut("@within(brightvl.aspect.logging.Logging)") // class
    public void loggingTypePointcut() {
    }

    @Around(value = "loggingMethodsPointcut() || loggingTypePointcut()")
    public Object loggingMethod(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        log.atLevel(properties.getLevel()).log("Before -> TimesheetService#{}", methodName);

        if (properties.isPrintArgs()) {
            Object[] args = pjp.getArgs();
            log.atLevel(properties.getLevel()).log("Arguments -> {}", args);
        }

        try {
            return pjp.proceed();
        } finally {
            log.atLevel(properties.getLevel()).log("After -> TimesheetService#{}", methodName);
        }
    }
}

