package com.github.svyaz.airlinersbot.adapter.response.dto;

import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TextResponseDto extends ResponseDto<SendMessage> {

    public TextResponseDto(SendMessage message, SendStrategy<SendMessage> sendStrategy) {
        super(message, sendStrategy);
    }
}
