package brightvl.spring.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

/*
AOP позволяет вклинивать свои методы в другие методы например логи
    Before
    AfterReturning
    AfterThrowing
    After = AfterReturning + AfterThrowing
    Around ->

    Bean = TimesheetService, obj = timesheetService
    proxyTimesheetService(obj)
*/


    // чтобы не прописывать каждый раз путь можно создать его и переиспользовать по имени
    @Pointcut("execution(* brightvl.spring.service.rest.TimesheetService.*(..))")
    public void timesheetServiceMethodsPointcut() {
    }

    //    Pointcut - точка входа в аспект
    //    @Before(value = "execution(* brightvl.spring.service.rest.TimesheetService.findById(Long))")
    //    @Before(value = "execution(* brightvl.spring.service.rest.TimesheetService.*(..))")
    @Before(value = "timesheetServiceMethodsPointcut()")
    public void beforeTimesheetServiceFindById(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        StringBuilder args = new StringBuilder();
        Object[] arguments = jp.getArgs();

        for (Object arg : arguments) {
            if (!args.isEmpty()) {
                args.append(", ");
            }
            args.append(arg.getClass().getSimpleName()).append(" = ").append(arg);
        }
        log.info("Before -> TimesheetService#{}({})", methodName,args);
    }

    @AfterThrowing(value = "timesheetServiceMethodsPointcut()", throwing = "ex")
    public void afterTimesheetServiceFindById(JoinPoint jp, Exception ex) {
        String methodName = jp.getSignature().getName();
        log.info("AfterThrowing -> TimesheetService#{} -> {}", methodName, ex.getClass().getName());
    }
}
