package com.github.svyaz.airlinersbot.adapter.response.dto;

import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendStrategy;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class PictureResponseDto extends ResponseDto<SendPhoto> {

    public PictureResponseDto(SendPhoto picture, SendStrategy<SendPhoto> sendStrategy) {
        super(picture, sendStrategy);
    }
}
