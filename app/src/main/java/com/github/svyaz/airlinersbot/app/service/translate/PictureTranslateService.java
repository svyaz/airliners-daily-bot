package com.github.svyaz.airlinersbot.app.service.translate;

import com.github.svyaz.airlinersbot.app.domain.Picture;

import java.util.function.BiFunction;

public interface PictureTranslateService extends BiFunction<Picture, String, Picture> {
}
