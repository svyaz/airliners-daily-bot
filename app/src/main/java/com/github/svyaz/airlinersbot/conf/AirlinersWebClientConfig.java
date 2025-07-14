package com.github.svyaz.airlinersbot.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
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
@Configuration
public class AirlinersWebClientConfig {

    @Value("${client.baseUrl}")
    private String baseUrl;

    @Value("${client.maxBufferSize}")
    private Integer maxBufferSize;

    @Value("${client.userAgent}")
    private String userAgent;

    @Value("${client.maxConnections}")
    private Integer maxConnections;

    @Value("${client.pendingAcquireTimeout}")
    private Integer pendingAcquireTimeout;

    @Value("${client.maxIdleTime}")
    private Integer maxIdleTime;

    @Value("${client.pendingAcquireMaxCount}")
    private Integer pendingAcquireMaxCount;

    @Bean(name = "airlinersWebClient")
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("airlinersConnectionPool")
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
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(c -> c.defaultCodecs().maxInMemorySize(maxBufferSize))
                        .build())
                .build();
    }
}
