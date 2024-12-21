package com.github.svyaz.airlinersbot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.svyaz.airlinersbot"})
public class BotApplication {

	@SneakyThrows
	public static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}

}
