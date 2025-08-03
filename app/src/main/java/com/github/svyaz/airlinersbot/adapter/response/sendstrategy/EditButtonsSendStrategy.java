package com.github.svyaz.airlinersbot.adapter.response.sendstrategy;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class EditButtonsSendStrategy implements SendStrategy<EditMessageReplyMarkup> {

    @Override
    public Message send(AbsSender sender, EditMessageReplyMarkup message) throws TelegramApiException {
        sender.execute(message);
        return null;
    }
}
