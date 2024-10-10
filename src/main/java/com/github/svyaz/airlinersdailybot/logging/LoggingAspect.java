package com.github.svyaz.airlinersdailybot.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

//    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);
//    @Pointcut("execution(* com.github.svyaz.airlinersdailybot.bot.AirlinersBot.onUpdateReceived(..))")
//    private void loggingReceivedUpdate() {
//    }

//    @Around("loggingReceivedUpdate()")
//    @Around("execution(* com.github.svyaz.airlinersdailybot.bot.AirlinersBot.onUpdateReceived(..))")
    /*@Around("@annotation(com.github.svyaz.airlinersdailybot.logging.LogMe)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info("{}() <- {}", methodName, Arrays.toString(args));
//        System.out.println("<-");
        Object result = joinPoint.proceed();
        log.info("{}() -> {}", methodName, result);
//        System.out.println("<-");
        return result;
    }*/

    @Before("@annotation(com.github.svyaz.airlinersdailybot.logging.LogMe)")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        log.info("{}() <- {}", methodName, Arrays.toString(args));
//        System.out.println("<-");
        //Object result = joinPoint.proceed();
        //log.info("{}() -> {}", methodName, result);
//        System.out.println("<-");
        //return result;
    }
}
