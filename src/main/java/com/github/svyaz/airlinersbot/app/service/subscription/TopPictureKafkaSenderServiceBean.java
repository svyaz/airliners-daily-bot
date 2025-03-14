package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.adapter.kafka.TopPictureKafkaSender;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopPictureKafkaSenderServiceBean implements TopPictureKafkaSenderService {

    private final UserService userService;

    private final TopPictureKafkaSender sender;

    @Override
    public void send(Picture picture) {
        userService.getUsersWithTopSubscription()
                .stream()
                .map(user -> new PictureMessage(user.getTlgUserId(), picture))
                .forEach(sender::send);
    }
}
