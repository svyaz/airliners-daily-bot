package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.adapter.htmlselector.HtmlSelector;
import com.github.svyaz.airlinersbot.adapter.htmlselector.PowChallengeDataExtractor;
import com.github.svyaz.airlinersbot.adapter.htmlselector.PowChallengeDataResolver;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class AirlinersClientBean implements AirlinersClient {

    private static final String POW_COOKIE_NAME = "pow_bypass";

    private final WebClient webClient;

    private final HtmlSelector htmlSelector;

    private final PowChallengeDataExtractor pcdExtractor;

    private final PowChallengeDataResolver pcdResolver;

    public AirlinersClientBean(
            @Qualifier("airlinersWebClient") WebClient webClient,
            HtmlSelector htmlSelector,
            PowChallengeDataExtractor pcdExtractor,
            PowChallengeDataResolver pcdResolver) {
        this.webClient = webClient;
        this.htmlSelector = htmlSelector;
        this.pcdExtractor = pcdExtractor;
        this.pcdResolver = pcdResolver;
    }

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

    @Override
    public Picture getPictureByUri(String uri) {
        return getQuery(uri)
                .map(htmlSelector::getPicture)
                .block();
    }

    /**
     * Executes GET request.
     * If body contains proof-of-work challenge script, resolves it and
     * executes second GET request with answer (cookie 'pow_bypass')
     */
    private Mono<String> getQuery(String uri) {
        var body1 = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);

        return body1.map(pcdExtractor)
                .map(pcdResolver)
                .flatMap(value -> webClient.get()
                        .uri(uri)
                        .cookie(POW_COOKIE_NAME, value)
                        .retrieve()
                        .bodyToMono(String.class)
                )
                .defaultIfEmpty(body1.toString());
    }

    private BodyInserters.FormInserter<String> getBodyForm(String keywords) {
        return BodyInserters.fromFormData("photo_search[keywords]", keywords)
                .with("photo_search[sortBy]", "dateAccepted")
                .with("photo_search[sortOrder]", "desc")
                .with("photo_search[perPage]", "36")
                .with("photo_search[display]", "detail");
    }

}
