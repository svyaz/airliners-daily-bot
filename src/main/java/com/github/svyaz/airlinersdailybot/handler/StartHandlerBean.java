package com.github.svyaz.airlinersdailybot.handler;

import com.github.svyaz.airlinersdailybot.conf.Constants;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Service(UpdateHandler.START_COMMAND_HANDLER)
public class StartHandlerBean extends AbstractUpdateHandler {

    @SneakyThrows
    @Override
    public void handle(Update update, AbsSender sender) {
        var text = Optional.of(update)
                .map(Update::getMessage)
                .map(Message::getFrom)
                .map(User::getFirstName)
                .map(name -> new Object[]{name})
                .map(args -> getMessage("start.text-with-name", args))
                .orElseGet(() -> getMessage("start.text", null));

        var message = SendMessage.builder()
                .parseMode(Constants.PARSE_MODE)
                .chatId(update.getMessage().getChatId())
                .text(text)
                .replyMarkup(getButtons())
                .build();

        sender.execute(message);
    }
}