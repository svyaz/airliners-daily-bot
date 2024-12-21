package com.github.svyaz.airlinersbot.app.domain.response;

import lombok.Data;

import java.util.List;

@Data
public abstract class Response {
    private final Long chatId;
    private final String text;
    private final List<List<InlineButton>> inlineButtons;
}
