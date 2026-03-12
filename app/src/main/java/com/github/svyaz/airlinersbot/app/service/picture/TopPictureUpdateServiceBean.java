package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.app.domain.PictureType;
import com.github.svyaz.airlinersbot.app.service.subscription.TopPictureProcessingService;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopPictureUpdateServiceBean implements TopPictureUpdateService {

    private final AirlinersClient airlinersClient;

    private final PictureStorageService pictureStorageService;

    private final TopPictureProcessingService processingService;

    @Override
    @Scheduled(
            initialDelayString = "${app.picture.update.initialDelay}",
            fixedDelayString = "${app.picture.update.fixedDelay}"
    )
    public void update() {
        log.info("update <-");

        var picture = airlinersClient.getTopPicture();

        pictureStorageService.find(picture.getId())
                .ifPresentOrElse(
                        p -> log.info("update -> already sent, id = {}", p.getId()),
                        () -> {
                            log.info("update -> save and send, id = {}", picture.getId());
                            picture.setPictureType(PictureType.TOP);
                            pictureStorageService.save(picture);
                            processingService.process(picture);
                        }
                );
    }

}
