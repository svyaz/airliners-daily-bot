package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.service.picture.TopPictureRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

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
                getLocalizedMessage("photo.caption", getCaptionArgs(picture)),
                List.of(
                        List.of(
                                new InlineButton(
                                        SHOW_TOP_CB_DATA,
                                        getLocalizedMessage("button.show-top")
                                )
                        )
                )
        );
    }

    private Object[] getCaptionArgs(Picture picture) {
        return new Object[]{
                // title with link
                Optional.ofNullable(picture.getPhotoPageUri())
                        .map(u -> String.format("<a href='%s'>%d</a>", u, picture.getId()))
                        .orElseGet(() -> String.format("%d", picture.getId())),
                // airline
                Optional.ofNullable(picture.getAirline()).orElse("-"),
                // aircraft
                Optional.ofNullable(picture.getAircraft()).orElse("-"),
                // registration
                Optional.ofNullable(picture.getRegistration()).orElse("-"),
                // location
                Optional.ofNullable(picture.getLocation()).orElse("-"),
                // date
                Optional.ofNullable(picture.getDate()).orElse("-"),
                // author
                Optional.ofNullable(picture.getAuthor()).orElse("-"),
                // author country
                Optional.ofNullable(picture.getAuthorCountry())
                        .map(ac -> String.format("(%s)", ac))
                        .orElse("")
        };
    }
}
