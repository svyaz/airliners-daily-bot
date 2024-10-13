package com.github.svyaz.airlinersdailybot.messages;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;

public interface PhotoCaptionBuilder {
    String build(PictureEntity entity, String langCode);
}
