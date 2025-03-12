package com.github.svyaz.airlinersbot.app.service.subscription;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface TopPictureSenderService {

    void send(Picture picture);
}
