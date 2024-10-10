package com.github.svyaz.airlinersdailybot.parser;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;

public interface HtmlParser {
    String getLargePicturePageUri(String html);

    PictureEntity getPictureData(String html);
}
