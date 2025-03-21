package com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard;

import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class KeyboardMapperBean implements KeyboardMapper {

    @Override
    public ReplyKeyboard apply(List<List<InlineButton>> buttons) {
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
