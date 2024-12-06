package com.github.svyaz.airlinersbot.adapter.dto.request;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.telegram.telegrambots.meta.api.objects.Update;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SearchRequestDto extends AbstractRequestDto {

    private final String keywords;

    public SearchRequestDto(Update update) {
        super(update.getMessage().getFrom(), update.getMessage());
        this.keywords = update.getMessage().getText();
    }
}
