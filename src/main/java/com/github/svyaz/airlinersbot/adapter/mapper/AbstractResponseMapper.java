package com.github.svyaz.airlinersbot.adapter.mapper;

import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractResponseMapper<T> implements ResponseMapper<T> {

    protected ReplyKeyboard mapReplyKeyboard(List<List<InlineButton>> buttons) {
        return Optional.ofNullable(buttons)
                .filter(Predicate.not(List::isEmpty))
                .map(rows -> rows.stream()
                        .map(
                                cols -> cols.stream()
                                        .map(this::mapInlineButton)
                                        .collect(Collectors.toList())
                        )
                        .collect(Collectors.toList())
                )
                .map(InlineKeyboardMarkup::new)
                .orElse(null);
    }

    private InlineKeyboardButton mapInlineButton(InlineButton button) {
        return InlineKeyboardButton.builder()
                .text(button.text())
                .callbackData(button.callbackData())
                .build();
    }

}
