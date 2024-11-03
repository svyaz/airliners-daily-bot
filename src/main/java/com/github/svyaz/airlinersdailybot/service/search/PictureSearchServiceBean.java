package com.github.svyaz.airlinersdailybot.service.search;

import com.github.svyaz.airlinersdailybot.errors.ParseException;
import com.github.svyaz.airlinersdailybot.mapper.PictureIdGetter;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import com.github.svyaz.airlinersdailybot.parser.HtmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureSearchServiceBean implements PictureSearchService {

    private final WebClient webClient;

    private final HtmlParser htmlParser;

    private final PictureIdGetter pictureIdGetter;

    @Override
    public PictureEntity search(String keywords) {
        try {
            var firstResultUri = getSearchFirstResultUri(keywords);
            var pictureEntity = getPictureEntity(firstResultUri);
            pictureEntity.setId(pictureIdGetter.getId(pictureEntity.getPictureData().getPhotoPageUri()));

            return pictureEntity;
        } catch (ParseException exception) {
            log.info("searchFirstResultUri: {}", exception.getMessage());
            return null; //todo message "nothing found"
        }
    }

    private String getSearchFirstResultUri(String keywords) {
        return webClient.post()
                .uri("search")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData("photo_search[keywords]", keywords)
                        .with("photo_search[sortBy]", "dateAccepted")
                        .with("photo_search[sortOrder]", "desc")
                        .with("photo_search[perPage]", "36")
                        .with("photo_search[display]", "detail")
                )
                .exchangeToMono(response -> {
                    if (response.statusCode().is3xxRedirection()) {
                        var redirectUrl = response.headers().header("Location").getFirst();
                        return getQuery(redirectUrl);
                    } else {
                        return response.bodyToMono(String.class);
                    }
                })
                .map(htmlParser::getFirstSearchResultUri)
                .block();
    }

    private Mono<String> getQuery(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    private PictureEntity getPictureEntity(String uri) {
        log.info("getPictureEntity <- {}", uri);
        return getQuery(uri)
                //.log()
                .map(htmlParser::getPictureData)
                .block();
    }

}
