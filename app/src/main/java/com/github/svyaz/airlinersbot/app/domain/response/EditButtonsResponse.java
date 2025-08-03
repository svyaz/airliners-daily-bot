package com.github.svyaz.airlinersbot.app.domain.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EditButtonsResponse extends Response {

    private final Integer messageId;

    public EditButtonsResponse(Long chatId, Integer messageId, List<List<InlineButton>> inlineButtons) {
        super(ResponseType.EDIT_BUTTONS, chatId, null, inlineButtons);
        this.messageId = messageId;
    }
}
