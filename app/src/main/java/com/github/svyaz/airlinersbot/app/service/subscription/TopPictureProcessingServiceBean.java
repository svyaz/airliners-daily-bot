package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.adapter.kafka.TopPictureKafkaSender;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.app.service.translate.PictureTranslateService;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopPictureProcessingServiceBean implements TopPictureProcessingService {

    private final UserService userService;

    private final TopPictureKafkaSender sender;

    private final PictureTranslateService translateService;

    @Override
    public void process(Picture picture) {
        userService.getUsersWithTopSubscription()
                .stream()
                .map(user -> new PictureMessage(
                        user.getTlgUserId(),
                        user.getLanguageCode(),
                        translateService.translate(picture, user.getLanguageCode()))
                )
                .forEach(sender::send);
    }

}
