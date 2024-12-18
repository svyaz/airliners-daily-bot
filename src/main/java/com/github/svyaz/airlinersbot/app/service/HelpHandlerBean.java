package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

@Service
public class HelpHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.HELP;
    }

    @Override
    public TextResponse handle(Request request) {
        return new TextResponse(
                request.message().getChatId(),
                getLocalizedMessage("help.text"),
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
