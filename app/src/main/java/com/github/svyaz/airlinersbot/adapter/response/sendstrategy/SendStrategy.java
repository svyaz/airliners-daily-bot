package com.github.svyaz.airlinersbot.adapter.response.sendstrategy;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface SendStrategy<T> {
    Message send(AbsSender sender, T message) throws TelegramApiException;
}
