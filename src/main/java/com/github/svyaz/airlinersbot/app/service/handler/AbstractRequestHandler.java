package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    protected MessageService messageService;

    @Autowired
    private UserService userService;

    abstract R getResponse(User user, Message message);

    @Override
    public R handle(Request request) {
        var updatedUser = userService.apply(request.user());
        return getResponse(updatedUser, request.message());
    }

    protected InlineButton getTopButton() {
        return new InlineButton(
                SHOW_TOP_CB_DATA,
                messageService.getLocalizedMessage("button.show-top")
        );
    }
}
