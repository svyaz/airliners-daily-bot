package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnknownHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.UNKNOWN_COMMAND;
    }

    @Override
    public TextResponse handle(Request request) {
        return Optional.ofNullable(request.user())
                .map(this::updateUser)
                .map(u -> new TextResponse(
                        u.getId(),
                        messageService.getLocalizedMessage("err.unknown-command"),
                        List.of(
                                List.of(
                                        getTopButton()
                                )
                        )
                ))
                .orElseThrow(() -> new RuntimeException("User not defined"));
    }
}
