package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

@Service
public class StartHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.START;
    }

    @Override
    TextResponse getResponse(User user, Message message) {
        return new TextResponse(
                user.getId(),
                Optional.ofNullable(user.getFirstName())
                        .map(name -> messageService.getLocalizedMessage("start.text-with-name", name))
                        .orElseGet(() -> messageService.getLocalizedMessage("start.text")),
                List.of(
                        List.of(
                                getTopButton()
                        )
                ));
    }
}
