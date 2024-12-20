package com.github.svyaz.airlinersdailybot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication(scanBasePackages = {"com.github.svyaz.airlinersdailybot"})
public class BotApplication {

	@SneakyThrows
	public static void main(String[] args) {
		var ctx = SpringApplication.run(BotApplication.class, args);

		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(ctx.getBean("airlinersBot", TelegramLongPollingBot.class));
	}

}
