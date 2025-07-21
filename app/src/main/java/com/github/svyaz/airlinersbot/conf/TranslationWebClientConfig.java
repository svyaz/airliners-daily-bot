package com.github.svyaz.airlinersbot.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "translation-client")
public class TranslationWebClientConfig {

    private static final String API_KEY_FORMAT = "Api-Key %s";

    private String baseUrl;

    private Integer maxBufferSize;

    private String userAgent;

    private Integer maxConnections;

    private Integer pendingAcquireTimeout;

    private Integer maxIdleTime;

    private Integer pendingAcquireMaxCount;

    private String apiKey;

    @Bean(name = "translationWebClient")
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("translationConnectionPool")
                .maxConnections(maxConnections)
                .pendingAcquireTimeout(Duration.ofMillis(pendingAcquireTimeout))
                .pendingAcquireMaxCount(pendingAcquireMaxCount)
                .maxIdleTime(Duration.ofMillis(maxIdleTime))
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .uriBuilderFactory(new CustomUriBuilderFactory(baseUrl))
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
                .defaultHeader(HttpHeaders.CACHE_CONTROL, "max-age=0")
                .defaultHeader(HttpHeaders.CONNECTION, "keep-alive")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, String.format(API_KEY_FORMAT, apiKey))
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxBufferSize))
                        .build())
                .build();
    }
}
