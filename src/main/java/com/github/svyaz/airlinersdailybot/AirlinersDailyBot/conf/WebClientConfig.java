package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class WebClientConfig {

    @Value("${client.baseUrl}")
    private String baseUrl;

    @Value("${client.maxBufferSize}")
    private Integer maxBufferSize;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxBufferSize))
                        .build())
                //todo which headers do we need?
                .build();
    }
}
