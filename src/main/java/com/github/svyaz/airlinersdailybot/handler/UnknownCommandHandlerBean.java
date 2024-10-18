package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.logging.LogAround;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Service(UpdateHandler.UNKNOWN_COMMAND_HANDLER)
public class UnknownCommandHandlerBean extends AbstractUpdateHandler {

    @LogAround
    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var message = SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text("I don't know this command!")
                //todo .replyMarkup(getKeyboardMarkup())
                .build();

        sender.execute(message);
    }
}
