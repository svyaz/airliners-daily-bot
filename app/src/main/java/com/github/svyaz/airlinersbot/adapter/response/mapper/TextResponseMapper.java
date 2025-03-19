package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.TextResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendTextStrategy;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class TextResponseMapper extends AbstractResponseMapper<TextResponseDto> {

    public TextResponseMapper(KeyboardMapper keyboardMapper) {
        super(keyboardMapper);
    }

    @Override
    public ResponseType myType() {
        return ResponseType.TEXT;
    }

    @Override
    public TextResponseDto apply(Response response) {
        return new TextResponseDto(
                SendMessage.builder()
                        .parseMode(Constants.PARSE_MODE)
                        .chatId(response.getChatId())
                        .text(response.getText())
                        .replyMarkup(keyboardMapper.apply(response.getInlineButtons()))
                        .build(),
                new SendTextStrategy()
        );
    }

}
