package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.app.domain.PictureType;
import com.github.svyaz.airlinersbot.app.service.subscription.TopPictureProcessingService;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Predicate;

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
        var currentPicture = pictureStorageService.getTop()
                .orElse(null);

        Optional.ofNullable(airlinersClient.getTopPicture())
                .filter(Predicate.not(picture -> picture.equals(currentPicture)))
                .ifPresent(picture -> {
                    picture.setPictureType(PictureType.TOP);
                    pictureStorageService.save(picture);
                    processingService.process(picture);
                });
    }
}
