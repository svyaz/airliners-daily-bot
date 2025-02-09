package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.request.resolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.app.service.handler.RequestHandler;
import com.github.svyaz.airlinersbot.conf.properties.BotProperties;
import com.github.svyaz.airlinersbot.adapter.locale.LocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot implements InitializingBean {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final RequestResolver requestResolver;
    private final Map<RequestType, RequestHandler<? extends Response>> requestHandlers;
    private final Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers;

    public AirlinersBot(BotProperties botProperties,
                        LocaleResolver localeResolver,
                        RequestResolver requestResolver,
                        Map<RequestType, RequestHandler<? extends Response>> requestHandlers,
                        Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers) {
        super(botProperties.getToken());
        this.botName = botProperties.getName();
        this.localeResolver = localeResolver;
        this.requestResolver = requestResolver;
        this.requestHandlers = requestHandlers;
        this.responseMappers = responseMappers;

        //log.info("handlers: {}", requestHandlers);
        //log.info("mappers: {}", responseMappers);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("onUpdateReceived <- {}", update);

        localeResolver.setLocale(update);

        Optional.of(update)
                .map(requestResolver)
                .flatMap(request -> Optional.ofNullable(requestHandlers.get(request.type()))
                        .map(handler -> handler.handle(request)))
                .flatMap(response -> Optional.ofNullable(responseMappers.get(response.getType()))
                        .map(mapper -> mapper.apply(response)))
                .ifPresent(this::sendSafe);
    }

    private Message sendSafe(ResponseDto<?> dto) {
        try {
            return dto.send(this);
        } catch (TelegramApiException e) {
            log.error("Error sending message: {}", dto, e);
            throw new RuntimeException("Message sending failed", e);
        }
    }

}
