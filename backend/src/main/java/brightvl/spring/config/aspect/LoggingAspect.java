package brightvl.spring.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {


    //Pointcut - точка входа в аспект
    @Before(value = "execution(* brightvl.spring.service.rest.TimesheetService.findById(Long))")
    public void beforeTimesheetServiceFindById(JoinPoint joinPoint) {
        Long id = (Long) joinPoint.getArgs()[0];
        log.info("TimesheetService#findById(),id = {}", id);
    }
}
