package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HelpHandlerBean extends AbstractRequestHandler {

    @Override
    public RequestType myType() {
        return RequestType.HELP;
    }

    @Override
    TextResponse getResponse(User user, String testText) {
        return new TextResponse(
                user.getTlgUserId(),
                messageService.getLocalizedMessage("help.text"),
                buttonsService.getButtons()
        );
    }
}
