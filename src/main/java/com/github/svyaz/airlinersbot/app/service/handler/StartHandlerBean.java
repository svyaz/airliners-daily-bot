package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

@Service
public class StartHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.START;
    }

    @Override
    public TextResponse handle(Request request) {
        var updatedUser = updateUser(request.user());

        return new TextResponse(
                updatedUser.getId(),
                Optional.ofNullable(updatedUser.getFirstName())
                        .map(name -> getLocalizedMessage("start.text-with-name", name))
                        .orElseGet(() -> getLocalizedMessage("start.text")),
                List.of(
                        List.of(
                                new InlineButton(
                                        SHOW_TOP_CB_DATA,
                                        getLocalizedMessage("button.show-top")
                                )
                        )
                ));
    }
}
