package com.github.svyaz.airlinersdailybot.bot;

import com.github.svyaz.airlinersdailybot.conf.BotConfig;
import com.github.svyaz.airlinersdailybot.handler.UpdateHandler;
import com.github.svyaz.airlinersdailybot.handler.UpdateHandlerResolver;
import com.github.svyaz.airlinersdailybot.logging.LogAround;
import com.github.svyaz.airlinersdailybot.mapper.LocaleResolver;
import com.github.svyaz.airlinersdailybot.messages.PhotoCaptionBuilder;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import com.github.svyaz.airlinersdailybot.service.PictureHolderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* TODO list:
 * 2. Выносим формирование сообщений в отдельный бин
 * 3. Отправку выносим в отдельный бин
 * 5. Команда /start
 * 6. Команда /help
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
    //private final PictureHolderService holderService;
    //private final PhotoCaptionBuilder captionBuilder;
    private final LocaleResolver localeResolver;
    private final UpdateHandlerResolver updateHandlerResolver;


    @Autowired
    public AirlinersBot(BotConfig botConfig,
                        LocaleResolver localeResolver,
                        UpdateHandlerResolver updateHandlerResolver
                        /*PictureHolderService holderService,
                        PhotoCaptionBuilder captionBuilder*/) {
        super(botConfig.getBotToken());
        this.botName = botConfig.getBotName();
        this.localeResolver = localeResolver;
        this.updateHandlerResolver = updateHandlerResolver;
//        this.holderService = holderService;
//        this.captionBuilder = captionBuilder;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional.of(update)
                .map(updateHandlerResolver::getHandler)
                .ifPresent(h -> h.handle(update, this));

        /*if (update.hasCallbackQuery()) {
            sendPlaneMessage(update);
        }

        if (update.hasMessage()) {
            sendUnknownCommandMessage(update);
        }*/
    }

//    @SneakyThrows
//    private void sendPlaneMessage(Update update) {
//        log.info("sendPlaneMessage <-");
//
//        var chatId = update.getCallbackQuery().getMessage().getChatId();
//        var langCode = Optional.ofNullable(update.getCallbackQuery())
//                .map(CallbackQuery::getFrom)
//                .map(User::getLanguageCode)
//                .orElse(null);
//
//        Optional.ofNullable(holderService.getEntity())
//                .map(entity -> buildSendPhoto(chatId, entity, langCode))
//                .map(this::executeSendPhoto)
//                .flatMap(message -> message.getPhoto().stream()
//                        .max(Comparator.comparing(PhotoSize::getWidth))
//                        .map(PhotoSize::getFileId)
//                )
//                .ifPresent(holderService::setFileId);
//        //todo orElse(сообщение об ошибке какое-то нужно)
//    }
//
//    @LogAround
//    @SneakyThrows
//    private void sendUnknownCommandMessage(Update update) {
//        log.info("sendUnknownCommandMessage <-");
//        //log.info("sendUnknownCommandMessage: lang: {}", update.getMessage().getFrom().getLanguageCode());
//
//        var msg = SendMessage.builder()
//                .chatId(update.getMessage().getChatId())
//                .text("I don't know this command!")
//                .replyMarkup(getKeyboardMarkup())
//                .build();
//
//        execute(msg);
//    }
//
//    private InlineKeyboardMarkup getKeyboardMarkup() {
//        return InlineKeyboardMarkup.builder()
//                .keyboard(List.of(
//                        List.of(InlineKeyboardButton.builder()
//                                .text("Show picture!")
//                                .callbackData("SHOW")
//                                .build())))
//                .build();
//    }
//
//    private SendPhoto buildSendPhoto(Long chatId, PictureEntity entity, String langCode) {
//        return SendPhoto.builder()
//                .chatId(chatId)
//                .parseMode("HTML")
//                .photo(holderService.getInputFile())
//                .caption(captionBuilder.build(entity, langCode))
//                .replyMarkup(getKeyboardMarkup())
//                .build();
//    }
//
//    @SneakyThrows
//    private Message executeSendPhoto(SendPhoto sendPhoto) {
//        return this.execute(sendPhoto);
//    }

}
