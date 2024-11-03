package com.github.svyaz.airlinersdailybot.service.top;

import com.github.svyaz.airlinersdailybot.logging.LogAround;
import com.github.svyaz.airlinersdailybot.mapper.PictureIdGetter;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import com.github.svyaz.airlinersdailybot.parser.HtmlParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureUpdateServiceBean implements PictureUpdateService {

    private final PictureHolderService holderService;
    private final WebClient webClient;
    private final HtmlParser htmlParser;
    private final PictureIdGetter pictureIdGetter;

    @LogAround
    @Override
    @Scheduled(initialDelay = 1_000, fixedDelay = 600_000)
    public void updatePictureIfNeed() {
        var topPhotoPageUri = getTopPhotoPageUri();
        var pictureId = pictureIdGetter.getId(topPhotoPageUri);

        if (holderService.hasChanged(pictureId)) {
            log.info("updatePictureIfNeed: picture has been changed, new id [{}]", pictureId);

            var pictureEntity = getPictureEntity(topPhotoPageUri);
            pictureEntity.setId(pictureId);
            holderService.setEntity(pictureEntity);
        }
    }

    private String getTopPhotoPageUri() {
        return webClient.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlParser::getLargePicturePageUri)
                .block();
    }

    private PictureEntity getPictureEntity(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .map(htmlParser::getPictureEntity)
                .block();   //todo Метод дублируется
    }

}
