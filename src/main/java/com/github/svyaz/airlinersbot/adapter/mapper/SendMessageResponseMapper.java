package com.github.svyaz.airlinersbot.adapter.mapper;

import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class SendMessageResponseMapper extends AbstractResponseMapper<SendMessage> {

    @Override
    public SendMessage apply(Response response) {
        return SendMessage.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(response.getChatId())
                .text(response.getText())
                .replyMarkup(mapReplyKeyboard(response.getInlineButtons()))
                .build();
    }
}
