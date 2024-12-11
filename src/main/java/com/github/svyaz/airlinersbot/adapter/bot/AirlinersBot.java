package com.github.svyaz.airlinersbot.adapter.bot;

import com.github.svyaz.airlinersbot.adapter.response.dto.ResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.ResponseMapper;
import com.github.svyaz.airlinersbot.adapter.request.resolver.RequestResolver;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.service.RequestHandler;
import com.github.svyaz.airlinersbot.conf.properties.BotProperties;
import com.github.svyaz.airlinersbot.adapter.locale.LocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;
import java.util.Set;

/*TODO:
 * Вопросы:
 * 1. BotProperties не залетел в конструктор.
 *    Это понятно - потому что не Spring-bean. Его надо руками new-кать?
 * 2. Не смог избавиться от <?> в конструкторе. Как применить маркерный интерфейс?
 *
 */
@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final RequestResolver requestResolver;
    private final Set<RequestHandler<?>> requestHandlers;
    private final Set<ResponseMapper<?, ?>> responseMappers;

    public AirlinersBot(BotProperties botProperties,
                        LocaleResolver localeResolver,
                        RequestResolver requestResolver,
                        Set<RequestHandler<?>> requestHandlers,
                        Set<ResponseMapper<?, ?>> responseMappers) {
        super(botProperties.getBotToken());
        this.botName = botProperties.getBotName();
        this.localeResolver = localeResolver;
        this.requestResolver = requestResolver;
        this.requestHandlers = requestHandlers;
        this.responseMappers = responseMappers;

//        log.info("handlers:  {}", requestHandlers);
//        log.info("mappers:  {}", responseMappers);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        localeResolver.setLocale(update); //todo: move to app?

        var message = Optional.of(update)
                .map(requestResolver)
                .map(request -> getHandler(request).handle(request))
                .map(response -> getMapper(response).map(response))
                .map(this::send)
                .orElse(null);

        log.debug("onUpdateReceived -> message [{}]", message);
    }

    private RequestHandler<?> getHandler(Request request) {
        return requestHandlers.stream()
                .filter(h -> request.type().equals(h.myType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no handler"));
    }

    private ResponseMapper<?, ?> getMapper(Response response) {
        return responseMappers.stream()
                .filter(m -> m.myType().equals(response.getClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no mapper for response"));
    }

    private Message send(ResponseDto<?> dto) {
        try {
            return dto.send(this);
        } catch (TelegramApiException exception) {
            throw new RuntimeException("send message failed");
        }
    }
}
