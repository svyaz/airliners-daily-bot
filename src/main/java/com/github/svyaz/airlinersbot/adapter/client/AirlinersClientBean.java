package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.adapter.htmlselector.HtmlSelector;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AirlinersClientBean implements AirlinersClient {

    private final WebClient webClient;

    private final HtmlSelector htmlSelector;

    @Override
    public Picture getTopPicture() {
        return getQuery("/")
                .map(htmlSelector::getTopPicturePageUri)
                .flatMap(this::getQuery)
                .map(htmlSelector::getTopPicture)
                .block();
    }

    private Mono<String> getQuery(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

}
