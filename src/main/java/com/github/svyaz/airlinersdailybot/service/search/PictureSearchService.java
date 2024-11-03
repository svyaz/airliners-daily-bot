package com.github.svyaz.airlinersdailybot.service.search;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;

public interface PictureSearchService {
    PictureEntity search(String keywords);
}
