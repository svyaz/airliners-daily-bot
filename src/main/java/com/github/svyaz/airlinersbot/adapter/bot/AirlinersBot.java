package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersdailybot.conf.properties.BotProperties;
import com.github.svyaz.airlinersdailybot.handler.UpdateHandlerResolver;
import com.github.svyaz.airlinersdailybot.service.LocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final UpdateHandlerResolver updateHandlerResolver;

    @Autowired
    public AirlinersBot(BotProperties botProperties,
                        LocaleResolver localeResolver,
                        UpdateHandlerResolver updateHandlerResolver) {
        super(botProperties.getBotToken());
        this.botName = botProperties.getBotName();
        this.localeResolver = localeResolver;
        this.updateHandlerResolver = updateHandlerResolver;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        localeResolver.setLocale(update);

        Optional.of(update)
                .map(updateHandlerResolver::getHandler)
                .ifPresent(h -> h.handle(update, this));
    }

}
