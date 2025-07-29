package com.github.svyaz.airlinersbot.conf;

import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.handler.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class RequestHandlersConfig {

    @Bean
    public Map<RequestType, RequestHandler> requestHandlers(
            Set<RequestHandler> handlers) {

        return handlers.stream()
                .collect(Collectors.toMap(RequestHandler::myType, Function.identity()));
    }
}
