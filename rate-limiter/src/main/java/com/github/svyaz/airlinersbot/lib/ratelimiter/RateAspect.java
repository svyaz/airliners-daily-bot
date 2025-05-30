package com.github.svyaz.airlinersbot.lib.ratelimiter;

import com.github.svyaz.airlinersbot.lib.ratelimiter.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateAspect {

    private final RateLimiterService rateLimiterService;

    @Around("@annotation(rateLimiter)")
    public Object rateLimiterAdvice(ProceedingJoinPoint joinPoint, RateLimiter rateLimiter) throws Throwable {
        Optional.of(rateLimiter.name())
                .map(rateLimiterService::getDelay)
                .filter(delay -> delay > 0L)
                .ifPresent(this::sleep);

        return joinPoint.proceed();
    }

    private void sleep(long delay) {
        try {
            log.debug("sleep <- delay: {}", delay);
            Thread.sleep(delay);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
