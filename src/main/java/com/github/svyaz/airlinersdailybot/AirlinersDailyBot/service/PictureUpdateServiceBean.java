package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.service;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.mapper.PictureToEntityMapper;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.db.PictureEntity;
import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.parser.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PictureUpdateServiceBean implements PictureUpdateService {

    private final WebClient webClient;
    private final HtmlParser htmlParser;

    private final PictureToEntityMapper entityMapper;

    @Override
    @Scheduled(initialDelay = 5_000, fixedDelay = 10_000)
    public void getPicture() {
        System.out.println("I went for picture update");

        var target = getTopPhotoLinkUri()
                .flatMap(this::getTargetData)
                .block();

                //.block();
    }

    private Mono<String> getTopPhotoLinkUri() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlParser::getLargePicturePageUri)
                .log();
    }

    private Mono<PictureEntity> getTargetData(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlParser::getTarget)
                .log();
    }
}
