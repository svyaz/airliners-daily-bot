package com.github.svyaz.airlinersbot.app.domain.response;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PictureResponse extends Response {

    private Picture picture;

    public PictureResponse(Long chatId, String text, Picture picture) {
        super(chatId, text, null);
        this.picture = picture;
    }
}
