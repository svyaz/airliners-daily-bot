package com.github.svyaz.airlinersbot.adapter.response.sendstrategy;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SendPhotoStrategy implements SendStrategy<SendPhoto> {

    @Override
    public Message send(AbsSender sender, SendPhoto photo) throws TelegramApiException {
        return sender.execute(photo);
    }
}
