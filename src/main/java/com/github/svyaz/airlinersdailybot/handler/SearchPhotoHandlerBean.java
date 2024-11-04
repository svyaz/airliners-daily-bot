package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import com.github.svyaz.airlinersdailybot.errors.ParseException;
import com.github.svyaz.airlinersdailybot.service.client.AirlinersClient;
import com.github.svyaz.airlinersdailybot.service.usercache.UserCacheHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@RequiredArgsConstructor
@Service(UpdateHandler.SEARCH_PHOTO_HANDLER)
public class SearchPhotoHandlerBean extends AbstractUpdateHandler {

    private final AirlinersClient airlinersClient;
    private final UserCacheHolder userCacheHolder;

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var keywords = update.getMessage().getText();
        var userId = update.getMessage().getFrom().getId();
        var chatId = update.getMessage().getChatId();

        try {
            var pictureEntity = airlinersClient.getFirstSearchResult(keywords);

            userCacheHolder.setUserNextSearchResultUri(
                    userId,
                    chatId,
                    pictureEntity.getNextPageUri()
            );

            sender.execute(SendPhoto.builder()
                    .parseMode(Constants.PARSE_MODE)
                    .chatId(chatId)
                    .photo(new InputFile(pictureEntity.getPhotoFileUri()))
                    .caption(getMessage("photo.caption", pictureEntity.getCaptionArgs()))
                    .replyMarkup(getButtons(pictureEntity))
                    .build());

        } catch (ParseException exception) {
            log.error(exception.getMessage());

            userCacheHolder.setUserNextSearchResultUri(userId, chatId, null);

            sender.execute(SendMessage.builder()
                    .parseMode(Constants.PARSE_MODE)
                    .chatId(chatId)
                    .text(getMessage("err.search.not-found", null))
                    .replyMarkup(getButtons())
                    .build());
        }
    }

}
