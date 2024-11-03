package com.github.svyaz.airlinersdailybot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

import static com.github.svyaz.airlinersdailybot.conf.Constants.*;
import static com.github.svyaz.airlinersdailybot.handler.UpdateHandler.*;

@Service
@RequiredArgsConstructor    //todo tests
public class UpdateHandlerResolverBean implements UpdateHandlerResolver {

    private final Map<String, UpdateHandler> handlers;

    @Override
    public UpdateHandler getHandler(Update update) {
        // Resolve commands
        if (update.hasMessage()) {
            return switch (update.getMessage().getText()) {
                case START_COMMAND -> handlers.get(START_COMMAND_HANDLER);
                case HELP_COMMAND -> handlers.get(HELP_COMMAND_HANDLER);
                default -> handlers.get(SEARCH_PHOTO_HANDLER);
            };
        }

        // Resolve callbacks
        if (update.hasCallbackQuery()) {
            var cbDataCommand = Optional.ofNullable(update.getCallbackQuery().getData())
                    .map(data -> data.split("#"))
                    .map(a -> a[0])
                    .orElse("");

            return switch (cbDataCommand) {
                case SHOW_TOP_CB_DATA -> handlers.get(TOP_PHOTO_HANDLER);
                case SHOW_NEXT_CB_DATA -> handlers.get(SEARCH_NEXT_PHOTO_HANDLER);
                default -> handlers.get(UNKNOWN_COMMAND_HANDLER);
            };
        }

        return handlers.get(UNKNOWN_COMMAND_HANDLER);
    }

}
