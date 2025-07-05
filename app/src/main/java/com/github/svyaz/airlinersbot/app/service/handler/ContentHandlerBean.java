package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.exception.PictureNotFoundException;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ContentHandlerBean extends AbstractRequestHandler<TextResponse> {

    private static final String ID_TEMPLATE = "\\d+$";

    private final PictureStorageService pictureStorageService;

    @Override
    public RequestType myType() {
        return RequestType.CONTENT;
    }

    @Override
    TextResponse getResponse(User user, String testText) {

        var pictureId = Optional.of(testText)
                .map(t -> Pattern.compile(ID_TEMPLATE).matcher(t))
                .filter(Matcher::find)
                .map(Matcher::group)
                .map(Long::parseLong)
                .orElseThrow(
                        () -> new PictureNotFoundException("No pictureId found in callback data")
                );

        return pictureStorageService.find(pictureId)
                .filter(picture -> StringUtils.hasText(picture.getContent()))
                .map(picture -> translateService.translate(picture, user.getLanguageCode()))
                .map(picture ->
                        new TextResponse(
                                user.getTlgUserId(),
                                messageService.getLocalizedMessage("photo.details", picture.getDetailsArgs()),
                                buttonsService.getButtons(picture)
                        )
                )
                .orElseThrow(
                        () -> new PictureNotFoundException("Picture or details not found for pictureId: " + pictureId)
                );
    }

}
