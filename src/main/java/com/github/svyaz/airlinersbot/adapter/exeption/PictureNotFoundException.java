package com.github.svyaz.airlinersbot.adapter.exeption;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(String message) {
        super(message);
    }
}
