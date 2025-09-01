package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.*;

public class HelpHandlerTest extends HandlersSpec {

    private static final String HELP_TEXT = "Help text";

    @Autowired
    HelpHandlerBean handler;

    @Test
    void handle_successful() {
        when(messageService.getLocalizedMessage(anyString()))
                .thenReturn(HELP_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        var user = User.builder()
                .tlgUserId(1L)
                .languageCode("ru")
                .build();

        var text = Constants.HELP_COMMAND;

        Assertions.assertEquals(
                new TextResponse(1L, HELP_TEXT, List.of()),
                handler.getResponse(user, text)
        );
    }

    @Test
    void myType_returns_HELP() {
        Assertions.assertEquals(
                RequestType.HELP,
                handler.myType()
        );
    }
}
