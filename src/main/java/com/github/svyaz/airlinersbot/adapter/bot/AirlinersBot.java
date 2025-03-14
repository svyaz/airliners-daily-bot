package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.request.resolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.app.service.handler.RequestHandler;
import com.github.svyaz.airlinersbot.conf.properties.BotProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component(AirlinersBot.BOT_COMPONENT_NAME)
public class AirlinersBot extends TelegramLongPollingBot implements InitializingBean, SubscriptionsSender {

    public static final String BOT_COMPONENT_NAME = "BOT_COMPONENT";

    private final Set<Long> inProgress;
    private final String botName;
    private final RequestResolver requestResolver;
    private final Map<RequestType, RequestHandler<? extends Response>> requestHandlers;
    private final Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers;

    public AirlinersBot(BotProperties botProperties,
                        RequestResolver requestResolver,
                        Map<RequestType, RequestHandler<? extends Response>> requestHandlers,
                        Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers) {
        super(botProperties.getToken());
        this.botName = botProperties.getName();
        this.requestResolver = requestResolver;
        this.requestHandlers = requestHandlers;
        this.responseMappers = responseMappers;

        this.inProgress = Collections.synchronizedSet(new HashSet<>());
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

        var request = requestResolver.apply(update);

        if (inProgress.contains(request.user().getId())) {
            return;
        }

        inProgress.add(request.user().getId());

        CompletableFuture.runAsync(() -> {
            try {
                LocaleContextHolder.setLocale(Locale.of(request.user().getLanguageCode()));

                Optional.of(request)
                        .flatMap(req -> Optional.ofNullable(requestHandlers.get(req.type()))
                                .map(handler -> handler.handle(req)))
                        .flatMap(resp -> Optional.ofNullable(responseMappers.get(resp.getType()))
                                .map(mapper -> mapper.apply(resp)))
                        .ifPresent(this::sendSafe);
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error while handling request", ex);
            } finally {
                inProgress.remove(request.user().getId());
            }
        });
    }

    @Override
    //todo: rate-limiter
    public void accept(Response response) {
        Optional.ofNullable(responseMappers.get(response.getType()))
                .map(mapper -> mapper.apply(response))
                .ifPresent(this::sendSafe);
    }

    private Message sendSafe(ResponseDto<?> dto) {
        try {
            log.debug("sendSafe <- {}", dto);
            return dto.send(this);
        } catch (TelegramApiException e) {
            log.error("Error sending message: {}", dto, e);
            throw new RuntimeException("Message sending failed", e);
        }
    }

}
