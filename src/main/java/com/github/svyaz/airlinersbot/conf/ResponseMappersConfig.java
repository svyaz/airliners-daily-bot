package com.github.svyaz.airlinersbot.conf;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ResponseMappersConfig {

    @Bean
    public Map<Class<? extends Response>, ResponseMapper<?, ?>> responseMappers(
            Set<ResponseMapper<? extends Response, ? extends ResponseDto<?>>> mappers) {

        return mappers.stream()
                .collect(Collectors.toMap(ResponseMapper::myType, Function.identity()));
    }

    /*@Bean
    public Map<Class<? extends Response>, ResponseMapper<? extends Response, ? extends ResponseDto<? extends PartialBotApiMethod<?>>>> responseMappers(
            Set<ResponseMapper<? extends Response, ? extends ResponseDto<? extends PartialBotApiMethod<?>>>> mappers) {

        return mappers.stream()
                .collect(Collectors.toMap(ResponseMapper::myType, Function.identity()));
    }*/
}
