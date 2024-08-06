package brightvl.spring.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class TimerAspect {

//  // Pointcut(execution (....controllers.*.*(..)))
//  public void handleException(Exception e) {
//    if (e.getClass().isAssignableFrom(NoSuchElementException.class)) {
//      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//    }
//  }


    //  Эта точка среза срабатывает на всех методах, помеченных аннотацией @Timer.
    @Pointcut("@annotation(brightvl.spring.config.aspect.Timer)") // method
    public void timerMethodsPointcut() {
    }

    @Pointcut("@within(brightvl.spring.config.aspect.Timer)") // class
    public void timerTypePointcut() {
    }

    //  объединяет две точки среза и выполняет код вокруг них.
    @Around(value = "timerMethodsPointcut() || timerTypePointcut()")
    public Object aroundTimesheetServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object target = pjp.getTarget();
        Method method = methodSignature.getMethod();

        boolean enabled = true;
        if (method.isAnnotationPresent(Timer.class)) {
            enabled = method.getAnnotation(Timer.class).enabled();
        } else if (target.getClass().isAnnotationPresent(Timer.class)) {
            enabled = target.getClass().getAnnotation(Timer.class).enabled();
        }

        if (!enabled) {
            return pjp.proceed(); // вызывает метод
        }

        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            log.info("TimesheetService#{} duration = {}ms", pjp.getSignature().getName(), duration);
        }
    }

}
