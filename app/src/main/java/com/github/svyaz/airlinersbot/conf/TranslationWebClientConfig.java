package com.github.svyaz.airlinersbot.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Setter
@Configuration
public class TranslationWebClientConfig {

    @Value("${translationClient.baseUrl}")
    private String baseUrl;

    @Value("${translationClient.maxBufferSize}")
    private Integer maxBufferSize;

    @Value(("${translationClient.userAgent}"))
    private String userAgent;

    @Bean(name = "translationWebClient")
    public WebClient webClient() {
        return WebClient.builder()
                .uriBuilderFactory(new CustomUriBuilderFactory(baseUrl))
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxBufferSize))
                        .build())
                .build();
    }
}
