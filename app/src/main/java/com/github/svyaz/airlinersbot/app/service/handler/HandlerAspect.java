package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.exception.CommonBotException;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerAspect {
    private final MessageService messageService;

    @Pointcut("execution(public * com.github.svyaz.airlinersbot.app.service.handler.*.handle(..))")
    private void handlerPointcut() {
    }

    @Around("handlerPointcut()")
    public Object proceedHandler(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (CommonBotException ex) {
            log.warn("proceedHandler -> {}", ex.getMessage());
            var request = (Request) joinPoint.getArgs()[0];
            return List.of(
                    new TextResponse(
                            request.user().getTlgUserId(),
                            messageService.getLocalizedMessage(ex.getMessageCode()),
                            List.of()
                    )
            );
        } catch (Throwable ex) {
            log.error("proceedHandler -> {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

}
