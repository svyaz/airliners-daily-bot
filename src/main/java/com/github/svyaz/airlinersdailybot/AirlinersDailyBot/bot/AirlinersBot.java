package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.bot;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.conf.BotConfig;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.service.PictureHolderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Comparator;
import java.util.List;

/* TODO list:
 * 1. При старте если не обновились данные - NPE. Сделать сообщение
 * + 2. Сохранение file_id после первой отправки картинки в holderService
 * 3. Логирование - на АОП!
 * 4. Шаблоны сообщений для caption - русский и английский. Java 21
 * 5. Команда /start
 * 6. Команда /info
 * 7. Обработка не командного текста
 * 8. ЛОгирование запросов (noSQL)
 * 9. Дату фотки заформатить на LocalDate
 * 10. Логирование апдейтера
 * 11. Тесты ;))
 * 12. Таймауты обновления - в конфиг
 * 13. Хостинг
 * 14. Тестовый бот!
 * */
@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;

    private final PictureHolderService holderService;

    @Autowired
    public AirlinersBot(BotConfig botConfig, PictureHolderService holderService) {
        super(botConfig.getBotToken());
        this.botName = botConfig.getBotName();
        this.holderService = holderService;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("onUpdateReceived <-");

        if (update.hasCallbackQuery()) {
            sendPlaneMessage(update);
        }

        if (update.hasMessage()) {
            sendUnknownCommandMessage(update);
        }
    }

    @SneakyThrows
    private void sendPlaneMessage(Update update) {
        log.debug("sendPlaneMessage <-");

        var data = holderService.getEntity().getPictureData();

        var msg = SendPhoto.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .photo(holderService.getInputFile())
                .caption(String.format("%s%nAirline: %s%nAircraft: %s",
                        data.getTitle(), data.getAirline(), data.getAircraft()
                ))
                .replyMarkup(getKeyboardMarkup())
                .build();

        var message = execute(msg);

        message.getPhoto().stream()
                .max(Comparator.comparing(PhotoSize::getWidth))
                .map(PhotoSize::getFileId)
                .ifPresent(holderService::setFileId);
    }

    @SneakyThrows
    private void sendUnknownCommandMessage(Update update) {
        var msg = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("I don't know this command!")
                .replyMarkup(getKeyboardMarkup())
                .build();

        execute(msg);
    }

    private InlineKeyboardMarkup getKeyboardMarkup() {
        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(
                        List.of(InlineKeyboardButton.builder()
                                .text("Show picture!")
                                .callbackData("SHOW")
                                .build())))
                .build();
    }

}
