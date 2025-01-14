package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.HELP;
    }

    @Override
    public TextResponse handle(Request request) {
        var updatedUser = updateUser(request.user());

        return new TextResponse(
                updatedUser.getId(),
                messageService.getLocalizedMessage("help.text"),
                List.of(
                        List.of(
                                getTopButton()
                        )
                ));
    }
}
