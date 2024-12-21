package com.github.svyaz.airlinersbot.app.domain.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TextResponse extends Response {

    public TextResponse(Long chatId, String text, List<List<InlineButton>> inlineButtons) {
        super(chatId, text, inlineButtons);
    }
}
