package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.exception.CommonBotException;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionsHandlerBean implements ExceptionsHandler {

    private final MessageService messageService;

    @Override
    public TextResponse handle(CommonBotException exception, Request request) {
        log.debug("handle <- request [{}], exception [{}]", request, exception.getMessage());

        return new TextResponse(
                request.user().getId(),
                messageService.getLocalizedMessage(exception.getMessageCode()),
                List.of()
        );
    }

}
