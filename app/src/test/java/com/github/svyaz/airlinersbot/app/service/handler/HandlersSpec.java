package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.SpringBootTestSpec;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.service.button.ButtonsService;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.translate.PictureTranslateService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.*;

public abstract class HandlersSpec extends SpringBootTestSpec {

    static final User user = User.builder()
            .tlgUserId(1L)
            .languageCode("ru")
            .build();

    @MockitoBean
    MessageService messageService;

    @MockitoBean
    ButtonsService buttonsService;

    @MockitoBean
    PictureTranslateService translateService;

    @MockitoBean
    UserService userService;

    @MockitoBean
    HandlerAspect handlerAspect;

    @BeforeEach
    void mock() {
        when(handlerAspect.proceedHandler(any()))
                .thenAnswer(invocation -> {
                    ProceedingJoinPoint joinPoint = invocation.getArgument(0);
                    return joinPoint.proceed();
                });

        when(userService.findAndUpdate(any()))
                .thenReturn(user);
    }
}
