package com.github.svyaz.airlinersdailybot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface UpdateHandler {

    String START_COMMAND_HANDLER = "START_COMMAND_HANDLER";
    String HELP_COMMAND_HANDLER = "HELP_HCOMMAND_ANDLER";
    String UNKNOWN_COMMAND_HANDLER = "UNKNOWN_COMMAND_HANDLER";
    String TOP_PHOTO_HANDLER = "TOP_PHOTO_HANDLER";
    String SEARCH_PHOTO_HANDLER = "SEARCH_PHOTO_HANDLER";
    String SEARCH_NEXT_PHOTO_HANDLER = "SEARCH_NEXT_PHOTO_HANDLER";

    void handle(Update update, AbsSender sender);
}
