package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.request.resolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.handler.RequestHandler;
import com.github.svyaz.airlinersbot.conf.properties.BotProperties;
import com.github.svyaz.airlinersbot.adapter.locale.LocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final RequestResolver requestResolver;
    private final Map<RequestType, RequestHandler<? extends Response>> requestHandlers;
    private final Map<Class<? extends Response>, ResponseMapper<? extends Response, ? extends ResponseDto<? extends BotApiMethodMessage>>> responseMappers;

    public AirlinersBot(BotProperties botProperties,
                        LocaleResolver localeResolver,
                        RequestResolver requestResolver,
                        Map<RequestType, RequestHandler<? extends Response>> requestHandlers,
                        Map<Class<? extends Response>, ResponseMapper<? extends Response, ? extends ResponseDto<? extends BotApiMethodMessage>>> responseMappers) {
        super(botProperties.getToken());
        this.botName = botProperties.getName();
        this.localeResolver = localeResolver;
        this.requestResolver = requestResolver;
        this.requestHandlers = requestHandlers;
        this.responseMappers = responseMappers;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        localeResolver.setLocale(update);

        var message = Optional.of(update)
                .map(requestResolver)
                .map(request -> requestHandlers.get(request.type()).handle(request))
                .map(response -> responseMappers.get(response.getClass()).map(response))
                .map(this::send)
                .orElse(null);

        //log.debug("onUpdateReceived -> message [{}]", message);
    }

    private Message send(ResponseDto<?> dto) {
        try {
            return dto.send(this);
        } catch (TelegramApiException exception) {
            throw new RuntimeException("send message failed");
        }
    }
}
