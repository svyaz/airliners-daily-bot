package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.TopPictureRequestService;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class TopHandlerTest extends HandlersSpec {

    private static final String CONTENT = "picture content";

    private static final Picture picture = Picture.builder()
            .id(2L)
            .content(CONTENT)
            .build();

    private static final Request request = new Request(
            RequestType.TOP,
            user,
            new Message(),
            Constants.SHOW_TOP_CB_DATA
    );

    @Autowired
    TopHandlerBean handler;

    @MockitoBean
    TopPictureRequestService topPictureRequestService;

    @Test
    void handle_successful() {
        when(topPictureRequestService.get())
                .thenReturn(picture);

        when(translateService.translate(any(), any()))
                .thenReturn(picture);

        when(messageService.getLocalizedMessage(anyString(), any(Object[].class)))
                .thenReturn(CONTENT);

        when(buttonsService.getButtons(any(), any()))
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new PictureResponse(1L, picture, CONTENT, List.of())),
                handler.handle(request)
        );
    }

    @Test
    void handle_returns_null_when_top_picture_not_found() {
        when(topPictureRequestService.get())
                .thenReturn(null);

        Assertions.assertEquals(
                Collections.singletonList(null),
                handler.handle(request)
        );
    }

    @Test
    void handle_returns_null_when_translate_service_returns_null() {
        when(topPictureRequestService.get())
                .thenReturn(picture);

        when(translateService.translate(any(), any()))
                .thenReturn(null);

        Assertions.assertEquals(
                Collections.singletonList(null),
                handler.handle(request)
        );
    }

    @Test
    void myType_returns_TOP() {
        Assertions.assertEquals(
                RequestType.TOP,
                handler.myType()
        );
    }
}
