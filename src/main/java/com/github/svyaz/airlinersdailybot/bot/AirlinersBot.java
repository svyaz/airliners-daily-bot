package com.github.svyaz.airlinersdailybot.bot;

import com.github.svyaz.airlinersdailybot.conf.BotConfig;
import com.github.svyaz.airlinersdailybot.handler.UpdateHandlerResolver;
import com.github.svyaz.airlinersdailybot.mapper.LocaleResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.Optional;

/* TODO list:
 * 2. Выносим формирование сообщений в отдельный бин
 * 3. Отправку выносим в отдельный бин
 * 7. Обработка не командного текста
 * 8. ЛОгирование запросов (noSQL)
 * 10. Ошибку парсинга где-то ловить
 * 11. Тесты ;))
 * 12. Таймауты обновления - в конфиг
 * 15. Фича с БД - запоминать какую последнюю картинку показывали юзеру и повторно не отправлять.
 *
 * */
@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;
    private final LocaleResolver localeResolver;
    private final UpdateHandlerResolver updateHandlerResolver;

    @Autowired
    public AirlinersBot(BotConfig botConfig,
                        LocaleResolver localeResolver,
                        UpdateHandlerResolver updateHandlerResolver) {
        super(botConfig.getBotToken());
        this.botName = botConfig.getBotName();
        this.localeResolver = localeResolver;
        this.updateHandlerResolver = updateHandlerResolver;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        localeResolver.setLocale(update);

        Optional.of(update)
                .map(updateHandlerResolver::getHandler)
                .ifPresent(h -> h.handle(update, this));
    }

}
