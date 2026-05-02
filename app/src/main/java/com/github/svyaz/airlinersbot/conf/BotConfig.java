package com.github.svyaz.airlinersbot.conf;

import com.github.svyaz.airlinersbot.adapter.bot.AirlinersBot;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import java.util.Optional;

@Data
@Component
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String name;

    private String token;

    private Proxy proxy;

    @Data
    public static class Proxy {
        private String type;
        private String host;
        private int port;
    }

    @Bean
    public AirlinersBot airlinersBot() {
        return new AirlinersBot(getOptions(), name, token);
    }

    private DefaultBotOptions getOptions() {
        var options = new DefaultBotOptions();

        Optional.ofNullable(proxy)
                .ifPresent(p -> {
                    options.setProxyType(DefaultBotOptions.ProxyType.valueOf(p.getType()));
                    options.setProxyHost(p.getHost());
                    options.setProxyPort(p.getPort());
                });

        return options;
    }

}
