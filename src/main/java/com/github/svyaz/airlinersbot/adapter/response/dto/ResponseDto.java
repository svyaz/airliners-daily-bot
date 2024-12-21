package com.github.svyaz.airlinersbot.adapter.response.dto;

import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Data
@AllArgsConstructor
public abstract class ResponseDto<T extends BotApiMethodMessage> {

    protected T telegramMessage;

    protected SendStrategy<T> sendStrategy;

    public Message send(AbsSender sender) throws TelegramApiException {
        return sendStrategy.send(sender, telegramMessage);
    }
}
