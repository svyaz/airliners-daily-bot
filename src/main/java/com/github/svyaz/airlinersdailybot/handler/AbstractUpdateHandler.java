package com.github.svyaz.airlinersdailybot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static com.github.svyaz.airlinersdailybot.conf.Constants.*;

public abstract class AbstractUpdateHandler implements UpdateHandler {

    @Autowired
    private MessageSource messageSource;

    String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    ReplyKeyboard getButtons() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(InlineKeyboardButton.builder()
                                .text(getMessage("button.show-top", null))
                                .callbackData(SHOW_TOP_CB_DATA)
                                .build())))
                .build();
    }
}
