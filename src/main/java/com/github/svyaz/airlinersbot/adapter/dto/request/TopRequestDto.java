package com.github.svyaz.airlinersbot.adapter.dto.request;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TopRequestDto extends AbstractRequestDto {

    public TopRequestDto(Update update) {
        super(update.getCallbackQuery().getFrom(), update.getCallbackQuery().getMessage());
    }
}
