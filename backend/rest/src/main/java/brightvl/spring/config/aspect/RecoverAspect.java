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
public class RecoverAspect {

    @Pointcut("@annotation(brightvl.spring.config.aspect.Recover) || @within(brightvl.spring.config.aspect.Recover)")
    public void recoverPointcut() {
    }

    @Around("recoverPointcut()")
    public Object handleRecover(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Recover recoverAnnotation = method.isAnnotationPresent(Recover.class) ?
                method.getAnnotation(Recover.class) :
                pjp.getTarget().getClass().getAnnotation(Recover.class);

        if (!recoverAnnotation.enabled()) {
            return pjp.proceed();
        }

        try {
            return pjp.proceed();
        } catch (Exception ex) {
            if (isExceptionExcluded(ex, recoverAnnotation.noRecoverFor())) {
                throw ex;
            }
            log.error("Recovering {}#{} after Exception[{}, \"{}\"]",
                    pjp.getTarget().getClass().getSimpleName(),
                    method.getName(),
                    ex.getClass().getName(),
                    ex.getMessage());
            return getDefaultReturnValue(methodSignature.getReturnType());
        }
    }

    private boolean isExceptionExcluded(Exception ex, Class<?>[] excludedExceptions) {
        for (Class<?> excludedException : excludedExceptions) {
            if (excludedException.isAssignableFrom(ex.getClass())) {
                return true;
            }
        }
        return false;
    }

    private Object getDefaultReturnValue(Class<?> returnType) {
        if (returnType.isPrimitive()) {
            if (returnType == boolean.class) {
                return false;
            } else if (returnType == char.class) {
                return '\u0000';
            } else if (Number.class.isAssignableFrom(returnType)) {
                return 0;
            }
        }
        return null;
    }
}
