package com.github.svyaz.airlinersbot.app.service;

import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import static com.github.svyaz.airlinersbot.app.service.HelpHandlerBean.requestType;

public abstract class AbstractRequestHandler<R extends Response> implements RequestHandler<R> {

    @Autowired
    private MessageSource messageSource;

    @Override
    public RequestType myType() {
        return requestType;
    }

    protected String getLocalizedMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    protected String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, null);
    }
}
