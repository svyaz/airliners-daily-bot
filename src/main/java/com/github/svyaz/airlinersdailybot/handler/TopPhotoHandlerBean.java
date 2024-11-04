package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import com.github.svyaz.airlinersdailybot.service.top.PictureHolderService;
import com.github.svyaz.airlinersdailybot.service.usercache.UserCacheHolder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Comparator;

@RequiredArgsConstructor
@Service(UpdateHandler.TOP_PHOTO_HANDLER)
public class TopPhotoHandlerBean extends AbstractUpdateHandler {

    private final PictureHolderService holderService;
    private final UserCacheHolder userCacheHolder;

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var userId = update.getCallbackQuery().getFrom().getId();
        var chatId = update.getCallbackQuery().getMessage().getChatId();

        userCacheHolder.setUserNextSearchResultUri(userId, chatId, null);

        var photo = SendPhoto.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(chatId)
                .photo(holderService.getInputFile())
                .caption(getMessage("photo.caption", holderService.getEntity().getCaptionArgs()))
                .replyMarkup(getButtons())
                .build();

        sender.execute(photo)
                .getPhoto()
                .stream()
                .max(Comparator.comparing(PhotoSize::getWidth))
                .map(PhotoSize::getFileId)
                .ifPresent(holderService::setFileId);
    }
}
