package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.SearchPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_NEXT_CB_DATA;

@Service
@RequiredArgsConstructor
public class SearchHandlerBean extends AbstractRequestHandler<PictureResponse> {

    private final SearchPictureService searchPictureService;

    @Override
    public RequestType myType() {
        return RequestType.SEARCH;
    }

    @Override
    public PictureResponse handle(Request request) {
        var updatedUser = updateUser(request.user());

        var picture = searchPictureService.search(updatedUser, request.message().getText());

        var buttonsRow = new ArrayList<InlineButton>();
        buttonsRow.add(getTopButton());

        Optional.ofNullable(picture.getNextPageUri())
                .ifPresent(uri -> buttonsRow.add(
                        new InlineButton(SHOW_NEXT_CB_DATA, messageService.getLocalizedMessage("button.search-next"))
                ));

        return new PictureResponse(
                updatedUser.getId(),
                picture,
                messageService.getLocalizedMessage("photo.caption", picture.getCaptionArgs()),
                List.of(buttonsRow)
        );
    }
}
