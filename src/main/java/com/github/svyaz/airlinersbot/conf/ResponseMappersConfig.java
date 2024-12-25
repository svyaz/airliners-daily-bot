package com.github.svyaz.airlinersbot.conf;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ResponseMappersConfig {

    @Bean
    public Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers(
            Set<ResponseMapper<? extends ResponseDto<?>>> mappers) {

        return mappers.stream()
                .collect(Collectors.toMap(ResponseMapper::myType, Function.identity()));
    }
}
