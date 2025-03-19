package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class UnknownHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.UNKNOWN_COMMAND;
    }

    @Override
    TextResponse getResponse(User user, Message message) {
        return new TextResponse(
                user.getTlgUserId(),
                messageService.getLocalizedMessage("err.unknown-command"),
                List.of(
                        List.of(
                                getTopButton()
                        )
                )
        );
    }
}
