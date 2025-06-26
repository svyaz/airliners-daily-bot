package com.github.svyaz.airlinersbot.app.service.translate;

import com.github.svyaz.airlinersbot.app.domain.Picture;

public interface PictureTranslateService {

    Picture translate(Picture picture, String langCode);
}
