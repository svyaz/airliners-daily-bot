package com.github.svyaz.airlinersbot.conf.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class BotProperties {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;
}
