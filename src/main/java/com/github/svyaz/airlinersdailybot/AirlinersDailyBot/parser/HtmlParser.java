package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.parser;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.db.PictureEntity;

public interface HtmlParser {
    String getLargePicturePageUri(String html);

    PictureEntity getTarget(String html);
}
