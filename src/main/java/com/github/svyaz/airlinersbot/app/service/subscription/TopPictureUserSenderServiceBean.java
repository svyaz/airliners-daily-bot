package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.adapter.bot.SubscriptionsSender;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopPictureUserSenderServiceBean implements TopPictureUserSenderService {

    private final SubscriptionsSender subscriptionsSender;

    private final MessageService messageService;

    @Override
    public void accept(PictureMessage message) {
        Optional.of(message)
                .map(msg -> new PictureResponse(
                        msg.getTlgUserId(),
                        msg.getPicture(),
                        messageService.getLocalizedMessage("photo.caption", msg.getPicture().getCaptionArgs()),
                        null))
                .ifPresent(subscriptionsSender);
    }
}
