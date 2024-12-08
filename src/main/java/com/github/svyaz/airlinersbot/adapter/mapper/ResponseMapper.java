package com.github.svyaz.airlinersbot.adapter.mapper;

import com.github.svyaz.airlinersbot.app.domain.response.Response;

import java.util.function.Function;

public interface ResponseMapper<T> extends Function<Response, T> {
}
