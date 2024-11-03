package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.svyaz.airlinersdailybot.conf.Constants.*;

public abstract class AbstractUpdateHandler implements UpdateHandler {

    @Autowired
    private MessageSource messageSource;

    String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    protected ReplyKeyboard getButtons(PictureEntity pictureEntity) {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        // Top picture
        buttons.add(InlineKeyboardButton.builder()
                .text(getMessage("button.show-top", null))
                .callbackData(SHOW_TOP_CB_DATA)
                .build());

        // Optional next of search results
        Optional.ofNullable(pictureEntity)
                .filter(entity -> Objects.nonNull(entity.getNextPageUri()))
                .ifPresent(entity ->
                        buttons.add(InlineKeyboardButton.builder()
                                .text(getMessage("button.search-next", null))
                                .callbackData(SHOW_NEXT_CB_DATA)
                                .build())
                );

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(buttons))
                .build();
    }

    protected ReplyKeyboard getButtons() {
        return getButtons(null);
    }
}
