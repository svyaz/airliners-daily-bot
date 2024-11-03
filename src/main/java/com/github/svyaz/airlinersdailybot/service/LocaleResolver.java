package com.github.svyaz.airlinersdailybot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface LocaleResolver {
    void setLocale(Update update);
}
