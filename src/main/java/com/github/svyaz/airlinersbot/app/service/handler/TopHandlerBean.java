package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.TopPictureRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public PictureResponse handle(Request request) {
        var updatedUser = updateUser(request.user());

        var picture = topPictureRequestService.get();

        return new PictureResponse(
                updatedUser.getId(),
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
