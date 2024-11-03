package com.github.svyaz.airlinersdailybot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Service(UpdateHandler.SEARCH_NEXT_PHOTO_HANDLER)
public class SearchNextPhotoHandlerBean extends AbstractUpdateHandler {

    @Override
    public void handle(Update update, AbsSender sender) {
        log.info("handle update <- {}", update);
    }
}
