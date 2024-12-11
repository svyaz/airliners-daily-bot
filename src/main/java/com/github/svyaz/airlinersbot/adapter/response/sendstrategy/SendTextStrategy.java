package com.github.svyaz.airlinersbot.adapter.response.sendstrategy;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendTextStrategy implements SendStrategy<SendMessage> {

    @Override
    public Message send(AbsSender sender, SendMessage message) throws TelegramApiException {
        return sender.execute(message);
    }
}
