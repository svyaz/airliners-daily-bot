package com.github.svyaz.airlinersbot.conf.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public final class BotProperties {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;
}
