package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.TopPictureRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopHandlerBean extends AbstractRequestHandler<PictureResponse> {

    private final TopPictureRequestService topPictureRequestService;

    @Override
    public RequestType myType() {
        return RequestType.TOP;
    }

    @Override
    PictureResponse getResponse(User user, Message message) {
        return Optional.ofNullable(topPictureRequestService.get())
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
