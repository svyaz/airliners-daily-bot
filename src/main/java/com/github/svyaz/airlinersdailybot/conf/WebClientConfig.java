package com.github.svyaz.airlinersdailybot.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Configuration
public class WebClientConfig {

    @Value("${client.baseUrl}")
    private String baseUrl;

    @Value("${client.maxBufferSize}")
    private Integer maxBufferSize;

    @Value(("${client.userAgent}"))
    private String userAgent;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .uriBuilderFactory(new CustomUriBuilderFactory(baseUrl))
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxBufferSize))
                        .build())
                .build();
    }
}
