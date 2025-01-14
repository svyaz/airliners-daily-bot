package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

public abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    protected MessageService messageService;

    @Autowired
    private UserService userService;

    protected User updateUser(User user) {
        return userService.apply(user);
    }

    protected InlineButton getTopButton() {
        return new InlineButton(
                SHOW_TOP_CB_DATA,
                messageService.getLocalizedMessage("button.show-top")
        );
    }
}
