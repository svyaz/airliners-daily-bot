package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.button.ButtonSupplier;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

@Transactional
abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    protected MessageService messageService;

    @Autowired
    private ButtonSupplier buttonSupplier;

    @Autowired
    private UserService userService;

    abstract R getResponse(User user, Message message);

    @Override
    public R handle(Request request) {
        var user = userService.findAndUpdate(request.user());
        R response = getResponse(user, request.message());
        userService.save(user);
        return response;
    }

    protected InlineButton getTopButton() {
        return buttonSupplier.getButton();
    }
}
