package com.github.svyaz.airlinersbot.adapter.response.dto;

import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendStrategy;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;

public class EditButtonsResponseDto extends ResponseDto<EditMessageReplyMarkup> {

    public EditButtonsResponseDto(EditMessageReplyMarkup message, SendStrategy<EditMessageReplyMarkup> sendStrategy) {
        super(message, sendStrategy);
    }
}
