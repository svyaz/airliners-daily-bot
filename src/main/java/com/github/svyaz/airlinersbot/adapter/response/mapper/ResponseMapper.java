package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;

import java.util.function.Function;

public interface ResponseMapper<R extends ResponseDto<?>> extends Function<Response, R> {

    ResponseType myType();
}
