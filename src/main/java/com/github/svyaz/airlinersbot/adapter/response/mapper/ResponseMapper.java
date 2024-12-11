package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;

public interface ResponseMapper<T extends Response, R extends ResponseDto<? extends BotApiMethodMessage>> {

    Class<T> myType();

    R map(Response response);
}
