package com.github.svyaz.airlinersdailybot.service.top;

import com.github.svyaz.airlinersdailybot.logging.LogAround;
import com.github.svyaz.airlinersdailybot.mapper.PictureIdGetter;
import com.github.svyaz.airlinersdailybot.service.client.AirlinersClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureUpdateServiceBean implements PictureUpdateService {

    private final PictureHolderService holderService;
    private final AirlinersClient airlinersClient;
    private final PictureIdGetter pictureIdGetter;

    @LogAround
    @Override
    @Scheduled(initialDelay = 1_000, fixedDelay = 600_000)
    public void updatePictureIfNeed() {
        var topPhotoPageUri = airlinersClient.getTopPhotoUri();
        var pictureId = pictureIdGetter.getId(topPhotoPageUri);

        if (holderService.hasChanged(pictureId)) {
            log.info("updatePictureIfNeed: picture has been changed, new id [{}]", pictureId);
            holderService.setEntity(
                    airlinersClient.getPictureEntity(topPhotoPageUri)
            );
        }
    }

}
