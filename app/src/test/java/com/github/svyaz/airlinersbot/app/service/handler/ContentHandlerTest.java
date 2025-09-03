package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.exception.PictureNotFoundException;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ContentHandlerTest extends HandlersSpec {

    private static final String CONTENT = "picture content";

    private static final Picture picture = Picture.builder()
            .id(2L)
            .content(CONTENT)
            .build();

    private static final Request request = new Request(
            RequestType.CONTENT,
            user,
            new Message(),
            String.format("%s-%d", Constants.SHOW_CONTENT_CB_DATA, 2L)
    );

    @Autowired
    ContentHandlerBean handler;

    @MockitoBean
    PictureStorageService pictureStorageService;

    @Test
    void handle_successful() {
        when(pictureStorageService.find(any()))
                .thenReturn(Optional.of(picture));

        when(translateService.translate(any(), any()))
                .thenReturn(picture);

        when(messageService.getLocalizedMessage(anyString(), any(Object[].class)))
                .thenReturn(CONTENT);

        when(buttonsService.getButtons(any(), any()))
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new TextResponse(1L, CONTENT, List.of())),
                handler.handle(request)
        );
    }

    @Test
    void handle_throws_when_callback_data_not_contains_id() {
        var wrongRequest = new Request(
                RequestType.CONTENT,
                user,
                new Message(),
                String.format("%s-%s", Constants.SHOW_CONTENT_CB_DATA, "somethingWrong")
        );

        var exception = Assertions.assertThrows(
                PictureNotFoundException.class,
                () -> handler.handle(wrongRequest)
        );

        Assertions.assertEquals(
                "No pictureId found in callback data",
                exception.getMessage()
        );
    }

    @Test
    void handle_throws_when_picture_not_found_in_db() {
        when(pictureStorageService.find(any()))
                .thenReturn(Optional.empty());

        var exception = Assertions.assertThrows(
                PictureNotFoundException.class,
                () -> handler.handle(request)
        );

        Assertions.assertEquals(
                "Picture or details not found for pictureId: 2",
                exception.getMessage()
        );
    }

    @Test
    void handle_throws_when_picture_has_no_content() {
        var nullContentPicture = Picture.builder()
                .id(2L)
                .content(null)
                .build();

        when(pictureStorageService.find(any()))
                .thenReturn(Optional.of(nullContentPicture));

        var exception = Assertions.assertThrows(
                PictureNotFoundException.class,
                () -> handler.handle(request)
        );

        Assertions.assertEquals(
                "Picture or details not found for pictureId: 2",
                exception.getMessage()
        );
    }

    @Test
    void handle_throws_when_translate_service_returns_null() {
        when(pictureStorageService.find(any()))
                .thenReturn(Optional.of(picture));

        when(translateService.translate(any(), any()))
                .thenReturn(null);

        var exception = Assertions.assertThrows(
                PictureNotFoundException.class,
                () -> handler.handle(request)
        );

        Assertions.assertEquals(
                "Picture or details not found for pictureId: 2",
                exception.getMessage()
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
