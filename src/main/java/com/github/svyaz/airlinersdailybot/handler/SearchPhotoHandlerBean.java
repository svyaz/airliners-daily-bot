package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import com.github.svyaz.airlinersdailybot.service.client.AirlinersClient;
import com.github.svyaz.airlinersdailybot.service.usercache.UserCacheHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@RequiredArgsConstructor
@Service(UpdateHandler.SEARCH_PHOTO_HANDLER)
public class SearchPhotoHandlerBean extends AbstractUpdateHandler {

    private final AirlinersClient airlinersClient;
    private final UserCacheHolder userCacheHolder;

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var keywords = update.getMessage().getText();

        //todo catch SearchException here ("nothing found")
        var pictureEntity = airlinersClient.getFirstSearchResult(keywords);

        userCacheHolder.setUserNextSearchResultUri(
                update.getMessage().getFrom().getId(),
                update.getMessage().getChatId(),
                pictureEntity.getNextPageUri()
        );

        var photo = SendPhoto.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(update.getMessage().getChatId())
                .photo(new InputFile(pictureEntity.getPhotoFileUri()))
                .caption(getMessage("photo.caption", pictureEntity.getCaptionArgs()))
                .replyMarkup(getButtons(pictureEntity))
                .build();

        sender.execute(photo);

        //todo если ничего не нашлось - надо сообщение отправить!
    }
}
