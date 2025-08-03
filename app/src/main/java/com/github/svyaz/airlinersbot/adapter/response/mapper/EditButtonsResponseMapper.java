package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.EditButtonsResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.EditButtonsSendStrategy;
import com.github.svyaz.airlinersbot.app.domain.response.EditButtonsResponse;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class EditButtonsResponseMapper extends AbstractResponseMapper<EditButtonsResponseDto> {

    public EditButtonsResponseMapper(KeyboardMapper keyboardMapper) {
        super(keyboardMapper);
    }

    @Override
    public ResponseType myType() {
        return ResponseType.EDIT_BUTTONS;
    }

    @Override
    public EditButtonsResponseDto apply(Response response) {
        var resp = (EditButtonsResponse) response;

        return new EditButtonsResponseDto(
                EditMessageReplyMarkup.builder()
                        .chatId(response.getChatId())
                        .messageId(resp.getMessageId())
                        .replyMarkup(new InlineKeyboardMarkup(List.of()))
                        .build(),
                new EditButtonsSendStrategy()
        );
    }

}
