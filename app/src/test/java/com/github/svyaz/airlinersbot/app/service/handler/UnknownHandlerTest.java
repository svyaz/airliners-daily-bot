package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class UnknownHandlerTest extends HandlersSpec {

    private static final String UNKNOWN_COMMAND_TEXT = "Unknown command text";

    private static final Request request = new Request(
            RequestType.UNKNOWN_COMMAND,
            user,
            new Message(),
            null
    );

    @Autowired
    UnknownHandlerBean handler;

    @Test
    void handle_successful() {
        when(messageService.getLocalizedMessage(anyString()))
                .thenReturn(UNKNOWN_COMMAND_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new TextResponse(1L, UNKNOWN_COMMAND_TEXT, List.of())),
                handler.handle(request)
        );
    }

    @Test
    void myType_returns_UNKNOWN() {
        Assertions.assertEquals(
                RequestType.UNKNOWN_COMMAND,
                handler.myType()
        );
    }
}
