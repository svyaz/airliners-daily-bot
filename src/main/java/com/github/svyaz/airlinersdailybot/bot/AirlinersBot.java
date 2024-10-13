package com.github.svyaz.airlinersdailybot.bot;

import com.github.svyaz.airlinersdailybot.conf.BotConfig;
import com.github.svyaz.airlinersdailybot.logging.LogAround;
import com.github.svyaz.airlinersdailybot.messages.PhotoCaptionBuilder;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import com.github.svyaz.airlinersdailybot.service.PictureHolderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* TODO list:
 * 1. Найти есть ли все-таки lang_code в callback.
 * 2. Выносим формирование сообщений в отдельный бин
 * 3. Отправку выносим в отдельный бин
 * +-4. Шаблоны сообщений для caption - русский и английский. Java 21
 *      в callback.message.user нету lang_code. Можно запоминать из присланных при подписке.
 *      (plugin String manipulation)
 * 5. Команда /start
 * 6. Команда /info
 * 7. Обработка не командного текста
 * 8. ЛОгирование запросов (noSQL)
 * 9. Дату фотки заформатить на LocalDate
 * 10. Ошибку парсинга где-то ловить
 * 11. Тесты ;))
 * 12. Таймауты обновления - в конфиг
 * +-13. Хостинг
 * 14. Тестовый бот!
 * 15. Фича с БД - запоминать какую последнюю картинку показывали юзеру и повторно не отправлять.
 *
 * */
@Slf4j
@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private static final String DEFAULT_LANG_CODE = "ru";   //todo move to config

    private final String botName;
    private final PictureHolderService holderService;
    private final PhotoCaptionBuilder captionBuilder;

    @Autowired
    public AirlinersBot(BotConfig botConfig,
                        PictureHolderService holderService,
                        PhotoCaptionBuilder captionBuilder) {
        super(botConfig.getBotToken());
        this.botName = botConfig.getBotName();
        this.holderService = holderService;
        this.captionBuilder = captionBuilder;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            sendPlaneMessage(update);
        }

        if (update.hasMessage()) {
            sendUnknownCommandMessage(update);
        }
    }

    @SneakyThrows
    private void sendPlaneMessage(Update update) {
        log.info("sendPlaneMessage <-");
        //log.info("sendPlaneMessage: lang: {}", update.getCallbackQuery().getMessage().getFrom().getLanguageCode());

        var chatId = update.getCallbackQuery().getMessage().getChatId();
        var langCode = Optional.ofNullable(update.getCallbackQuery().getMessage())
                .map(Message::getFrom)
                .map(User::getLanguageCode)
                .orElse(DEFAULT_LANG_CODE);

        Optional.ofNullable(holderService.getEntity())
                //.map(PictureEntity::getPictureData)
                .map(entity -> buildSendPhoto(chatId, entity, langCode))
                .map(this::executeSendPhoto)
                .flatMap(message -> message.getPhoto().stream()
                        .max(Comparator.comparing(PhotoSize::getWidth))
                        .map(PhotoSize::getFileId)
                )
                .ifPresent(holderService::setFileId);
        //todo orElse(сообщение об ошибке какое-то нужно)
    }

    @LogAround
    @SneakyThrows
    private void sendUnknownCommandMessage(Update update) {
        log.info("sendUnknownCommandMessage <-");
        //log.info("sendUnknownCommandMessage: lang: {}", update.getMessage().getFrom().getLanguageCode());

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

    private SendPhoto buildSendPhoto(Long chatId, PictureEntity entity, String langCode) {
        return SendPhoto.builder()
                .chatId(chatId)
                .parseMode("HTML")
                .photo(holderService.getInputFile())
                .caption(captionBuilder.build(entity, langCode))
                .replyMarkup(getKeyboardMarkup())
                .build();
    }

    @SneakyThrows
    private Message executeSendPhoto(SendPhoto sendPhoto) {
        return this.execute(sendPhoto);
    }

}
