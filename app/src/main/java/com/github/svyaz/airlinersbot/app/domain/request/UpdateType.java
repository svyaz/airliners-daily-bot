package com.github.svyaz.airlinersbot.app.domain.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum UpdateType {
    COMMAND(
            Update::hasMessage,
            u -> u.getMessage().getText(),
            u -> u.getMessage().getFrom(),
            Update::getMessage,
            false),

    CALLBACK_BUTTON(
            Update::hasCallbackQuery,
            u -> u.getCallbackQuery().getData(),
            u -> u.getCallbackQuery().getFrom(),
            u -> u.getCallbackQuery().getMessage(),
            true),

    UNKNOWN(    //предполагаем что неизвестная команда содержит сообщение
            update -> true,
            u -> Optional.ofNullable(u.getMessage()).map(Message::getText).orElse(null),
            u -> Optional.ofNullable(u.getMessage()).map(Message::getFrom).orElse(null),
            Update::getMessage,
            false);

    /**
     * Filter that defines incoming update type
     */
    final Predicate<Update> filter;

    /**
     * Text/callback data getter
     */
    final Function<Update, String> testTextGetter;

    /**
     * Telegram user getter
     */
    final Function<Update, User> tlgUserGetter;

    /**
     * Message-from getter
     */
    final Function<Update, Message> messageGetter;

    /**
     * Delete previous message buttons
     */
    final boolean clearButtons;
}
