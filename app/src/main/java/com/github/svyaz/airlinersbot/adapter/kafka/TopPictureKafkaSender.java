package com.github.svyaz.airlinersbot.adapter.kafka;

import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;

public interface TopPictureKafkaSender {

    void send(PictureMessage message);
}
