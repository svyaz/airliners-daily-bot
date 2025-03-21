package com.github.svyaz.airlinersbot.adapter.response.mapper;

import com.github.svyaz.airlinersbot.adapter.response.dto.PictureResponseDto;
import com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard.KeyboardMapper;
import com.github.svyaz.airlinersbot.adapter.response.sendstrategy.SendPhotoStrategy;
import com.github.svyaz.airlinersbot.app.domain.response.PictureResponse;
import com.github.svyaz.airlinersbot.app.domain.response.Response;
import com.github.svyaz.airlinersbot.app.domain.response.ResponseType;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Component
public class PictureResponseMapper extends AbstractResponseMapper<PictureResponseDto> {

    public PictureResponseMapper(KeyboardMapper keyboardMapper) {
        super(keyboardMapper);
    }

    @Override
    public ResponseType myType() {
        return ResponseType.PHOTO;
    }

    @Override
    public PictureResponseDto apply(Response response) {
        var resp = (PictureResponse) response;

        return new PictureResponseDto(
                SendPhoto.builder()
                        .parseMode(Constants.PARSE_MODE)
                        .chatId(resp.getChatId())
                        .photo(new InputFile(resp.getPicture().getPhotoFileUri()))
                        .caption(resp.getText())
                        .replyMarkup(keyboardMapper.apply(resp.getInlineButtons()))
                        .build(),
                new SendPhotoStrategy()
        );
    }
}
