package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.mapper;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.PictureEntity;
import org.springframework.stereotype.Component;

@Component
public class PictureToEntityMapperBean implements PictureToEntityMapper {

    @Override
    public PictureEntity apply(String html) {
        return new PictureEntity();
    }
}
