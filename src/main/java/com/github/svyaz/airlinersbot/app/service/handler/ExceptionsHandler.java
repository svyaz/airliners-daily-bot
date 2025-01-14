package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.exception.CommonBotException;

public interface ExceptionsHandler {

    TextResponse handle(CommonBotException exception, Request request);
}
