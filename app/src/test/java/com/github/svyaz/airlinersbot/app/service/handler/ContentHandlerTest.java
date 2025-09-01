package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ContentHandlerTest extends HandlersSpec {

    private static final String CONTENT = "picture content";

    @Autowired
    ContentHandlerBean handler;

    @MockitoBean
    PictureStorageService pictureStorageService;

    //todo : complete tests

    @Test
    void handle_successful() {
        var picture = Picture.builder()
                .id(2L)
                .content(CONTENT)
                .build();

        when(pictureStorageService.find(any()))
                .thenReturn(Optional.of(picture));

        when(translateService.translate(any(), any()))
                .thenReturn(picture);

        when(messageService.getLocalizedMessage(anyString(), any(Object[].class)))
                .thenReturn(CONTENT);

        when(buttonsService.getButtons(any(), any()))
                .thenReturn(List.of());

        var user = User.builder()
                .tlgUserId(1L)
                .languageCode("ru")
                .build();

        var text = String.format("%s-%d", Constants.SHOW_CONTENT_CB_DATA, 2L);

        Assertions.assertEquals(
                new TextResponse(1L, CONTENT, List.of()),
                handler.getResponse(user, text)
        );
    }

    @Test
    void myType_returns_CONTENT() {
        Assertions.assertEquals(
                RequestType.CONTENT,
                handler.myType()
        );
    }
}
