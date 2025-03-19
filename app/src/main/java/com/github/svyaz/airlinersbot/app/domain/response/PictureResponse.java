package com.github.svyaz.airlinersbot.app.domain.response;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PictureResponse extends Response {

    private Picture picture;

    public PictureResponse(Long chatId, Picture picture, String text, List<List<InlineButton>> inlineButtons) {
        super(ResponseType.PHOTO, chatId, text, inlineButtons);
        this.picture = picture;
    }
}
