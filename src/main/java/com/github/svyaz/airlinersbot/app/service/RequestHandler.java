package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;

public interface RequestHandler<R extends Response> {

    RequestType myType();

    R handle(Request request);
}
