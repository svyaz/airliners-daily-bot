package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service(UpdateHandler.HELP_COMMAND_HANDLER)
public class HelpHandlerBean extends AbstractUpdateHandler {

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var message = SendMessage.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(update.getMessage().getChatId())
                .text(getMessage("help.text", null))
                .replyMarkup(getButtons())
                .build();

        sender.execute(message);
    }
}
