package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import com.github.svyaz.airlinersdailybot.service.search.PictureSearchService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service(UpdateHandler.SEARCH_PHOTO_HANDLER)
public class SearchPhotoHandlerBean extends AbstractUpdateHandler {

    @Autowired
    private PictureSearchService searchService;

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var keywords = update.getMessage().getText();

        var pictureEntity = searchService.search(keywords);

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
