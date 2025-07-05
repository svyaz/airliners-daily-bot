package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContentHandlerTest extends HandlersSpec {

    @Autowired
    ContentHandlerBean handler;

    //todo : complete tests

    @Test
    void handle_successful() {
        var user = User.builder()
                .tlgUserId(1L)
                .languageCode("ru")
                .build();

        var text = String.format("%s-%d", Constants.SHOW_CONTENT_CB_DATA, 2);

        Assertions.assertEquals(
                handler.getResponse(user, text),
                new TextResponse(1L, "text", List.of())
        );
    }
}
