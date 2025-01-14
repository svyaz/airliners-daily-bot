package com.github.svyaz.airlinersbot.app.exception;

import lombok.Getter;

@Getter
public abstract class CommonBotException extends RuntimeException {

    public final String messageCode;

    protected CommonBotException(String messageCode, String message) {
        super(message);
        this.messageCode = messageCode;
    }
}
