package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.bot;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.conf.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AirlinersBot extends TelegramLongPollingBot {

    private final String botName;

    @Autowired
    public AirlinersBot(BotConfig botConfig) {
        super(botConfig.getBotToken());
        this.botName = botConfig.getBotName();
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());

        try {
            var msg = new SendMessage();
            msg.setChatId(update.getMessage().getChatId());
            msg.setText("https://www.airliners.net/photo/AeroMexico/737-MAX-9/7682469/L");
            execute(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

}
