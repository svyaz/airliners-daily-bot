package com.github.svyaz.airlinersbot.app.service.message;

public interface MessageService {

    String getLocalizedMessage(String code, Object... args);

    default String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, new Object[]{});
    }
}
