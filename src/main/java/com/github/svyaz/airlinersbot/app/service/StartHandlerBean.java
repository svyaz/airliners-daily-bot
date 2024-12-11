package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

@Service
public class StartHandlerBean extends AbstractRequestHandler<TextResponse> {

    static final RequestType requestType = RequestType.START;

    @Override
    public TextResponse handle(Request request) {

        // 1. save user
        // 2. create start message with/without name

        /*return new TextResponse(
                request.message().getChatId(),
                getLocalizedMessage("help.text"),
                List.of(
                        List.of(
                                new InlineButton(
                                        SHOW_TOP_CB_DATA,
                                        getLocalizedMessage("button.show-top")
                                )
                        )
                ));*/
        return null;
    }
}
