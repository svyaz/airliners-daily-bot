package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.adapter.htmlselector.HtmlSelector;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

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
                .map(htmlSelector::getPicture)
                .block();
    }

    @Override
    public Picture search(String keywords) {
        return webClient.post()
                .uri("search")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(getBodyForm(keywords))
                .exchangeToMono(resp -> Optional.of(resp)
                        .filter(r -> r.statusCode().is3xxRedirection())
                        .map(r -> r.headers().header("Location").getFirst())
                        .map(this::getQuery)
                        .orElseGet(() -> resp.bodyToMono(String.class))
                )
                .map(htmlSelector::getFirstSearchResultUri)
                .flatMap(this::getQuery)
                .map(htmlSelector::getPicture)
                .block();
    }

    private Mono<String> getQuery(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    private BodyInserters.FormInserter<String> getBodyForm(String keywords) {
        return BodyInserters.fromFormData("photo_search[keywords]", keywords)
                .with("photo_search[sortBy]", "dateAccepted")
                .with("photo_search[sortOrder]", "desc")
                .with("photo_search[perPage]", "36")
                .with("photo_search[display]", "detail");
    }

}
