package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class StartHandlerTest extends HandlersSpec {

    private static final String START_TEXT = "Start text";
    private static final String START_TEXT_WITH_NAME = "Start text with name";

    @Autowired
    StartHandlerBean handler;

    @Test
    void handle_without_user_first_name_successful() {
        when(messageService.getLocalizedMessage(anyString()))
                .thenReturn(START_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        var request = new Request(
                RequestType.START,
                user,
                new Message(),
                Constants.START_COMMAND
        );

        Assertions.assertEquals(
                List.of(new TextResponse(1L, START_TEXT, List.of())),
                handler.handle(request)
        );
    }

    @Test
    void handle_with_user_first_name_successful() {
        var userWithFirstName = User.builder()
                .tlgUserId(1L)
                .firstName("Elvis")
                .languageCode("ru")
                .build();

        when(messageService.getLocalizedMessage(anyString()))
                .thenReturn(START_TEXT_WITH_NAME);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        var request = new Request(
                RequestType.START,
                userWithFirstName,
                new Message(),
                Constants.START_COMMAND
        );

        Assertions.assertEquals(
                List.of(new TextResponse(1L, START_TEXT_WITH_NAME, List.of())),
                handler.handle(request)
        );
    }

    @Test
    void myType_returns_START() {
        Assertions.assertEquals(
                RequestType.START,
                handler.myType()
        );
    }
}
