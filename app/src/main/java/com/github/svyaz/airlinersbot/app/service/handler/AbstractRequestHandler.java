package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.EditButtonsResponse;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.button.ButtonsService;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import com.github.svyaz.airlinersbot.app.service.translate.PictureTranslateService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract class AbstractRequestHandler implements RequestHandler {

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected ButtonsService buttonsService;

    @Autowired
    protected PictureTranslateService translateService;

    @Autowired
    private UserService userService;

    abstract Response getResponse(User user, String testText);

    @Override
    public List<Response> handle(Request request) {
        var user = userService.findAndUpdate(request.user());

        List<Response> responses = new ArrayList<>();

        Optional.ofNullable(request.message())
                .map(Message::getMessageId)
                .filter(msgId -> request.type().getUpdateType().isClearButtons())                .map(msgId -> new EditButtonsResponse(user.getTlgUserId(), msgId, null))
                .ifPresent(responses::add);

        responses.add(getResponse(user, request.testText()));
        userService.save(user);
        return responses;
    }

}
