package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.SearchPictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_NEXT_CB_DATA;

@Service
@RequiredArgsConstructor
public class SearchNextHandlerBean extends AbstractRequestHandler<PictureResponse> {

    private final SearchPictureService searchPictureService;

    @Override
    public RequestType myType() {
        return RequestType.SEARCH_NEXT;
    }

    @Override
    PictureResponse getResponse(User user, Message message) {
        var picture = searchPictureService.next(user);

        var buttonsRow = new ArrayList<InlineButton>();
        buttonsRow.add(getTopButton());

        Optional.ofNullable(picture.getNextPageUri())
                .ifPresent(uri -> buttonsRow.add(
                        new InlineButton(SHOW_NEXT_CB_DATA, messageService.getLocalizedMessage("button.search-next"))
                ));

        return new PictureResponse(
                user.getId(),
                picture,
                messageService.getLocalizedMessage("photo.caption", picture.getCaptionArgs()),
                List.of(buttonsRow)
        );
    }
}
