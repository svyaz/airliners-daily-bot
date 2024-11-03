package com.github.svyaz.airlinersdailybot.service.client;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;

public interface AirlinersClient {

    String getTopPhotoUri();

    PictureEntity getFirstSearchResult(String keywords);

    PictureEntity getPictureEntity(String uri);
}
