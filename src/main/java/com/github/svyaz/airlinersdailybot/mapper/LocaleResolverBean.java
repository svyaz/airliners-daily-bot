package com.github.svyaz.airlinersdailybot.mapper;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Locale;
import java.util.Optional;

@Component
public class LocaleResolverBean implements LocaleResolver {

    @Override
    public void setLocale(Update update) {
        Optional.of(update)
                .filter(Update::hasMessage)
                .map(Update::getMessage)
                .map(Message::getFrom)
                .map(User::getLanguageCode)
                .map(Locale::of)
                .ifPresent(LocaleContextHolder::setLocale);

        Optional.of(update)
                .filter(Update::hasCallbackQuery)
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getFrom)
                .map(User::getLanguageCode)
                .map(Locale::of)
                .ifPresent(LocaleContextHolder::setLocale);
    }
}
