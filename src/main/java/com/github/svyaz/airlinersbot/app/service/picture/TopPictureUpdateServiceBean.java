package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.datastore.service.PictureStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TopPictureUpdateServiceBean implements TopPictureUpdateService {

    private final AirlinersClient airlinersClient;

    private final PictureStorageService pictureStorageService;

    @Override
    @Scheduled(
            initialDelayString = "${app.picture.update.initialDelay}",
            fixedDelayString = "${app.picture.update.fixedDelay}"
    )
    public void update() {
        Optional.ofNullable(airlinersClient.getTopPicture())
                .ifPresent(pictureStorageService::save);
    }
}
