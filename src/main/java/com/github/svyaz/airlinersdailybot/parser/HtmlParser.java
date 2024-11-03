package com.github.svyaz.airlinersdailybot.parser;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;

public interface HtmlParser {
    String getLargePicturePageUri(String html);

    String getFirstSearchResultUri(String html);

    PictureEntity getPictureData(String html);
}
