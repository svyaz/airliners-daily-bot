package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import com.github.svyaz.airlinersdailybot.service.search.PictureSearchService;
import com.github.svyaz.airlinersdailybot.service.usercache.UserCacheHolder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Service(UpdateHandler.SEARCH_NEXT_PHOTO_HANDLER)
public class SearchNextPhotoHandlerBean extends AbstractUpdateHandler {

    @Autowired
    private UserCacheHolder userCacheHolder;

    @Autowired
    private PictureSearchService pictureSearchService;

    @Override
    @SneakyThrows
    public void handle(Update update, AbsSender sender) {

        var userId = update.getCallbackQuery().getFrom().getId();
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        var nextUri = userCacheHolder.getUserNextSearchResultUri(userId, chatId);
        var pictureEntity = pictureSearchService.getPictureEntity(nextUri);

        userCacheHolder.setUserNextSearchResultUri(userId, chatId, pictureEntity.getNextPageUri());

        var photo = SendPhoto.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(chatId)
                .photo(new InputFile(pictureEntity.getPhotoFileUri()))
                .caption(getMessage("photo.caption", pictureEntity.getCaptionArgs()))
                .replyMarkup(getButtons(pictureEntity))
                .build();

        sender.execute(photo);
    }
}
