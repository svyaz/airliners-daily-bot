package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.adapter.bot.SubscriptionsSender;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.app.service.button.ButtonSupplier;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopPictureUserSenderServiceBean implements TopPictureUserSenderService {

    private final SubscriptionsSender subscriptionsSender;

    private final MessageService messageService;

    private final ButtonSupplier buttonSupplier;

    @Override
    public void accept(PictureMessage message) {
        Optional.of(message)
                .map(PictureMessage::getLanguageCode)
                .map(Locale::of)
                .ifPresent(LocaleContextHolder::setLocale);

        Optional.of(message)
                .map(msg -> new PictureResponse(
                        msg.getTlgUserId(),
                        msg.getPicture(),
                        messageService.getLocalizedMessage("photo.caption", msg.getPicture().getCaptionArgs()),
                        List.of(List.of(buttonSupplier.getButton()))
                ))
                .ifPresent(subscriptionsSender);
    }
}
