package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

@Service
public class HelpHandlerBean implements RequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.HELP;
    }

    @Override
    public TextResponse handle(Request request) {
        return new TextResponse(request.message().getChatId(), "Hi", null);
    }
}
