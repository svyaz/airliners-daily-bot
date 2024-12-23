package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.app.domain.response.Response;

public interface ResponseMapper<T extends Response, R extends ResponseDto<?>> {

    Class<T> myType();

    R map(T response);
}
