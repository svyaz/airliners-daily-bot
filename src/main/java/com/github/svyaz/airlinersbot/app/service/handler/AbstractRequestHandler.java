package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    protected String getLocalizedMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    protected String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, new Object[]{});
    }

    protected User updateUser(User user) {
        return userService.apply(user);
    }
}
