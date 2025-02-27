package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.TopPictureRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

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
        var picture = topPictureRequestService.get();

        return new PictureResponse(
                user.getId(),
                picture,
                messageService.getLocalizedMessage("photo.caption", picture.getCaptionArgs()),
                List.of(
                        List.of(
                                getTopButton()
                        )
                )
        );
    }

}
