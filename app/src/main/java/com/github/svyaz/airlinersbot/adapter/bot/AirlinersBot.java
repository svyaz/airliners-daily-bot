package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.request.resolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.app.exception.CommonBotException;
import com.github.svyaz.airlinersbot.app.service.handler.RequestHandler;
import com.github.svyaz.airlinersbot.lib.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class AirlinersBot extends TelegramLongPollingBot implements InitializingBean, SubscriptionsSender {

    private final Set<Long> inProgress;
    private final String botName;

    @Autowired private RequestResolver requestResolver;
    @Autowired private Map<RequestType, RequestHandler> requestHandlers;
    @Autowired private Map<ResponseType, ResponseMapper<? extends ResponseDto<?>>> responseMappers;

    public AirlinersBot(DefaultBotOptions options,
            String botName,
            String botToken) {
        super(options, botToken);
        this.botName = botName;
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
    @Transactional(noRollbackFor = CommonBotException.class)
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
                                .map(handler -> handler.handle(req))
                        )
                        .stream()
                        .flatMap(List::stream)
                        .map(resp ->
                                responseMappers.get(resp.getType()).apply(resp)
                        )
                        .forEach(this::sendSafe);
            } catch (Exception ex) {
                Thread.currentThread().interrupt();
                log.warn("onUpdateReceived: {}", ex.getMessage());
                throw new RuntimeException("Error while handling request", ex);
            } finally {
                inProgress.remove(request.user().getId());
            }
        });
    }

    @Override
    @RateLimiter(name = "subscrSendingLimiter")
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
