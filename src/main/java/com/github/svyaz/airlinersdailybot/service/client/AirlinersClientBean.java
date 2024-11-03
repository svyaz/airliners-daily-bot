package com.github.svyaz.airlinersdailybot.service.client;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import com.github.svyaz.airlinersdailybot.parser.HtmlParser;
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
    private final HtmlParser htmlParser;

    @Override
    public String getTopPhotoUri() {
        return getQuery("/")
                .map(htmlParser::getLargePicturePageUri)
                .block();
    }

    @Override
    public PictureEntity getFirstSearchResult(String keywords) {
        var firstResultUri = webClient.post()
                .uri("search")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("photo_search[keywords]", keywords)
                        .with("photo_search[sortBy]", "dateAccepted")
                        .with("photo_search[sortOrder]", "desc")
                        .with("photo_search[perPage]", "36")
                        .with("photo_search[display]", "detail")
                )
                .exchangeToMono(resp -> Optional.of(resp)
                        .filter(r -> r.statusCode().is3xxRedirection())
                        .map(r -> r.headers().header("Location").getFirst())
                        .map(this::getQuery)
                        .orElseGet(() -> resp.bodyToMono(String.class))
                )
                .map(htmlParser::getFirstSearchResultUri)
                .block();

        return getPictureEntity(firstResultUri);
    }

    @Override
    public PictureEntity getPictureEntity(String uri) {
        return getQuery(uri)
                .map(htmlParser::getPictureEntity)
                .block();
    }

    private Mono<String> getQuery(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }
}
