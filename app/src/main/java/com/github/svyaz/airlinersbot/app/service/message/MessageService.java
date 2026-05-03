package com.github.svyaz.airlinersbot.app.service.message;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface MessageService {

    String getLocalizedMessage(String code, Object... args);

    default String getLocalizedMessage(String code) {
        return getLocalizedMessage(code, new Object[]{});
    }

    String getFullPictureCaption(Picture picture);
}
