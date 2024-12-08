package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.requestresolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.service.RequestHandler;
import com.github.svyaz.airlinersbot.conf.properties.BotProperties;
import com.github.svyaz.airlinersdailybot.service.LocaleResolver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final RequestResolver requestResolver;
    private final Set<RequestHandler<?>> requestHandlers;
    private final ResponseMapper<SendMessage> sendMessageResponseMapper;
    private final ResponseMapper<SendPhoto> sendPhotoResponseMapper;

    public AirlinersBot(BotProperties botProperties,
                        LocaleResolver localeResolver,
                        RequestResolver requestResolver,
                        Set<RequestHandler<?>> requestHandlers,
                        ResponseMapper<SendMessage> sendMessageResponseMapper,
                        ResponseMapper<SendPhoto> sendPhotoResponseMapper) {
        super(botProperties.getBotToken());
        this.botName = botProperties.getBotName();
        this.localeResolver = localeResolver;
        this.requestResolver = requestResolver;
        this.requestHandlers = requestHandlers;
        this.sendMessageResponseMapper = sendMessageResponseMapper;
        this.sendPhotoResponseMapper = sendPhotoResponseMapper;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        localeResolver.setLocale(update); //todo: move to app?

        Optional.of(update)
                .map(requestResolver)
                .map(request -> getHandler(request)
                        .handle(request))
                .map(this::send);
    }

    private RequestHandler<?> getHandler(Request request) {
        return requestHandlers.stream()
                .filter(h -> request.type().equals(h.myType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no handler"));
    }

    @SneakyThrows
    private Message send(Response response) {
        if (response instanceof TextResponse textResponse) {
            return Optional.of(response)
                    .map(sendMessageResponseMapper)
                    .map(this::execute)
                    .orElseThrow(() -> new RuntimeException("execute(SendMessage) failed"));
        } else if (response instanceof PictureResponse pictureResponse) {
            return Optional.of(response)
                    .map(sendPhotoResponseMapper)
                    .map(this::execute)
                    .orElseThrow(() -> new RuntimeException("execute(SendPhoto) failed"));
        } else {
            throw new RuntimeException("unknown response type");
        }
    }
}
