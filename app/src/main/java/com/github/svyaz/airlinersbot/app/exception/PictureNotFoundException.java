package com.github.svyaz.airlinersbot.app.exception;

public class PictureNotFoundException extends CommonBotException {

    public PictureNotFoundException(String message) {
        super("err.search.not-found", message);
    }
}
