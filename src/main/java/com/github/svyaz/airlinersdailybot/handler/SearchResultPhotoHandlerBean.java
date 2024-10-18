package com.github.svyaz.airlinersdailybot.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service(UpdateHandler.SEARCH_RESULT_PHOTO_HANDLER)
public class SearchResultPhotoHandlerBean extends AbstractUpdateHandler {

    @Override
    public void handle(Update update, AbsSender sender) {

    }
}
