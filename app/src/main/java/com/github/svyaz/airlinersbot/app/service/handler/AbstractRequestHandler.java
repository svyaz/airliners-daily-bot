package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.button.ButtonsService;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.translate.PictureTranslateService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected ButtonsService buttonsService;

    @Autowired
    protected PictureTranslateService translateService;

    @Autowired
    private UserService userService;

    abstract R getResponse(User user, String testText);

    @Override
    public R handle(Request request) {
        var user = userService.findAndUpdate(request.user());
        R response = getResponse(user, request.testText());
        userService.save(user);
        return response;
    }

}
