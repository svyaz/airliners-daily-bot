package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;

import java.util.function.Consumer;

public interface TopPictureUserSenderService extends Consumer<PictureMessage> {
}
