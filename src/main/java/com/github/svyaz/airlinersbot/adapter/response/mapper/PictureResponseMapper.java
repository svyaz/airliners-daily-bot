package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.PictureResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendPhotoStrategy;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
public class PictureResponseMapper extends AbstractResponseMapper<PictureResponse, PictureResponseDto> {

    public PictureResponseMapper(KeyboardMapper keyboardMapper) {
        super(keyboardMapper);
    }

    @Override
    public ResponseType myType() {
        return ResponseType.PHOTO;
    }

    @Override
    public PictureResponseDto apply(PictureResponse response) {
        return new PictureResponseDto(
                SendPhoto.builder()
                        .parseMode(Constants.PARSE_MODE)
                        .chatId(response.getChatId())
                        .photo(new InputFile(response.getPicture().getPhotoFileUri()))
                        .caption(response.getText())
                        .replyMarkup(keyboardMapper.apply(response.getInlineButtons()))
                        .build(),
                new SendPhotoStrategy()
        );
    }
}
