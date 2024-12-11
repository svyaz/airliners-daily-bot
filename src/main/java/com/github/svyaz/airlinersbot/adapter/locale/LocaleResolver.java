package com.github.svyaz.airlinersbot.adapter.locale;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface LocaleResolver {
    void setLocale(Update update);
}
