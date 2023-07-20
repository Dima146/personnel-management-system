package com.company.managementsystem.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DebugLoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugLoggingAspect.class);

    @Pointcut("within(com.company.managementsystem.controller..*)")
    public void controllerMethods() {
    }

    @Before("controllerMethods()")
    public void logBeforeExecution(JoinPoint joinPoint) {
        LOGGER.debug("Executing method: " + joinPoint.getSignature());
    }

    @AfterReturning(value = "controllerMethods()", returning = "object")
    public void logAfterReturningValue(JoinPoint joinPoint, Object object) {
        LOGGER.debug("Method " + joinPoint.getSignature() +
                " has successfully been executed. Value: [" + object + "]"
                + " has been returned");
    }
}
