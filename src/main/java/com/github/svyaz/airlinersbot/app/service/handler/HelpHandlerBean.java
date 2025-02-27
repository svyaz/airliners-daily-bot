package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
public class HelpHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.HELP;
    }

    @Override
    TextResponse getResponse(User user, Message message) {
        return new TextResponse(
                user.getId(),
                messageService.getLocalizedMessage("help.text"),
                List.of(
                        List.of(
                                getTopButton()
                        )
                ));
    }
}
