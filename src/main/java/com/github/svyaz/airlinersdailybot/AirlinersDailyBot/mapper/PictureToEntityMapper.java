package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.mapper;

import com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.PictureEntity;

import java.util.function.Function;

public interface PictureToEntityMapper extends Function<String, PictureEntity> {
}
