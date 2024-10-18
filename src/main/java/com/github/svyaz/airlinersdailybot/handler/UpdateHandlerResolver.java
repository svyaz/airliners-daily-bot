package com.github.svyaz.airlinersdailybot.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandlerResolver {

    UpdateHandler getHandler(Update update);
}
