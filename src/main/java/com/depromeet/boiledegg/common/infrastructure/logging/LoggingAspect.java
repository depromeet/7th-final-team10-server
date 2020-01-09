package com.depromeet.boiledegg.common.infrastructure.logging;

import lombok.experimental.UtilityClass;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
final class LoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    void restControllerPointcut() {
    }

    @Pointcut("!restControllerPointcut() || within(com.depromeet.boiledegg..*)")
    void loggingPointcut() {
    }

    @Around("restControllerPointcut()")
    Object controllerAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var log = LoggerPool.get(joinPoint);

        logEnter(
                log,
                joinPoint
        );

        final var result = joinPoint.proceed();

        logExit(
                log,
                joinPoint,
                result
        );

        return result;
    }

    // TODO @Around("loggingPointcut()")
    // final 없애야해서 일단 보류
    Object loggingPointcutAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final var log = LoggerPool.get(joinPoint);

        if (LoggingCondition.isNotUtilMethod(joinPoint)) {
            logEnter(
                    log,
                    joinPoint
            );
        }

        final var result = joinPoint.proceed();

        if (LoggingCondition.isNotUtilMethod(joinPoint)) {
            logExit(
                    log,
                    joinPoint,
                    result
            );
        }

        return result;
    }


    private void logEnter(
            final Logger log,
            final ProceedingJoinPoint joinPoint
    ) {
        log.debug("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs())
        );
    }

    private void logExit(
            final Logger log,
            final ProceedingJoinPoint joinPoint,
            final Object result
    ) {
        log.debug("Exit: {}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result
        );
    }

    @UtilityClass
    private static class LoggingCondition {

        private static final String GETTER_METHOD_PREFIX = "get";
        private static final String SETTER_METHOD_PREFIX = "set";
        private static final String TO_STRING_METHOD_PREFIX = "toString";
        private static final String EQUALS_METHOD_PREFIX = "equals";
        private static final String HASH_CODE_METHOD_PREFIX = "hashCode";

        private boolean isNotUtilMethod(final ProceedingJoinPoint joinPoint) {
            final var name = joinPoint.getSignature().getName();

            return !name.startsWith(GETTER_METHOD_PREFIX) &&
                    !name.startsWith(SETTER_METHOD_PREFIX) &&
                    !name.equals(TO_STRING_METHOD_PREFIX) &&
                    !name.equals(EQUALS_METHOD_PREFIX) &&
                    !name.equals(HASH_CODE_METHOD_PREFIX);
        }
    }

    @UtilityClass
    private static class LoggerPool {

        private Logger get(final ProceedingJoinPoint joinPoint) {
            final var target = joinPoint.getTarget();
            return LoggerFactory.getLogger(target.getClass());
        }
    }
}
