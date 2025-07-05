package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.SearchPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchNextHandlerBean extends AbstractRequestHandler<PictureResponse> {

    private final SearchPictureService searchPictureService;

    @Override
    public RequestType myType() {
        return RequestType.SEARCH_NEXT;
    }

    @Override
    PictureResponse getResponse(User user, String testText) {
        return Optional.ofNullable(searchPictureService.next(user))
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
