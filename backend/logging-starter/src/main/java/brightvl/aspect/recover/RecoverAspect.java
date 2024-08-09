package brightvl.aspect.recover;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RecoverAspect {

    private final RecoverProperties recoverProperties;

    @Around("@annotation(brightvl.aspect.recover.Recover)")
    public Object recover(ProceedingJoinPoint pjp) throws Throwable {
        if (!recoverProperties.isEnabled()) {
            return pjp.proceed();
        }

        try {
            return pjp.proceed();
        } catch (Throwable ex) {
            if (recoverProperties.getNoRecoverFor().contains(ex.getClass().getName())) {
                throw ex;
            }

            log.error("Recovering from exception: {}", ex.getMessage());
            // Здесь можно добавить код для восстановления
            return null;
        }
    }
}


/*
Пример применения
<dependency>
    <groupId>brightvl</groupId>
    <artifactId>recover-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>



application:
  logging:
    enabled: true
    level: DEBUG
    printArgs: true

  recover:
    enabled: true
    noRecoverFor:
      - java.lang.IllegalArgumentException
      - java.io.IOException

*/