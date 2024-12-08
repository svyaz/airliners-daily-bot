package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

@Service
public class HelpHandlerBean implements RequestHandler<TextResponse> {

    private final RequestType requestType = RequestType.HELP;

    @Override
    public TextResponse handle(Request request) {
        return null;
    }

}
