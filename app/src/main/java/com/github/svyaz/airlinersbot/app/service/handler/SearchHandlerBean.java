package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.SearchPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchHandlerBean extends AbstractRequestHandler<PictureResponse> {

    private final SearchPictureService searchPictureService;

    @Override
    public RequestType myType() {
        return RequestType.SEARCH;
    }

    @Override
    PictureResponse getResponse(User user, Message message) {
        return Optional.ofNullable(message.getText())
                .map(text -> searchPictureService.search(user, text))
                .map(picture -> translateService.translate(picture, user.getLanguageCode()))
                .map(picture ->
                        new PictureResponse(
                                user.getTlgUserId(),
                                picture,
                                messageService.getLocalizedMessage("photo.caption", picture.getCaptionArgs()),
                                buttonsService.getButtons(picture)
                        )
                )
                .orElse(null);
    }

}
