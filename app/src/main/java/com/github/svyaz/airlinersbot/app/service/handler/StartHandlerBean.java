package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StartHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.START;
    }

    @Override
    TextResponse getResponse(User user, String testText) {
        return new TextResponse(
                user.getTlgUserId(),
                Optional.ofNullable(user.getFirstName())
                        .map(name -> messageService.getLocalizedMessage("start.text-with-name", name))
                        .orElseGet(() -> messageService.getLocalizedMessage("start.text")),
                buttonsService.getButtons()
        );
    }
}
