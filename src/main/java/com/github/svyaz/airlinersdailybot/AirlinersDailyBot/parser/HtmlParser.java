package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.parser;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.PictureEntity;

public interface HtmlParser {
    String getLargePicturePageUri(String html);

    PictureEntity getPictureData(String html);
}
