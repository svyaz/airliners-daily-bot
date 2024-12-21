package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;

@AllArgsConstructor
public abstract class AbstractResponseMapper<T extends Response, R extends ResponseDto<? extends BotApiMethodMessage>> implements ResponseMapper<T, R> {

    protected KeyboardMapper keyboardMapper;
}
