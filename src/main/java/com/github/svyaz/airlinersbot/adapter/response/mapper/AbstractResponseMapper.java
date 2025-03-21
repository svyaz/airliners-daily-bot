package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractResponseMapper<R extends ResponseDto<?>> implements ResponseMapper<R> {

    protected KeyboardMapper keyboardMapper;
}
