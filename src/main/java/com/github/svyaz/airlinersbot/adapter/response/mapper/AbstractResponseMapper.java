package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractResponseMapper<T extends Response, R extends ResponseDto<?>> implements ResponseMapper<T, R> {

    protected KeyboardMapper keyboardMapper;
}
