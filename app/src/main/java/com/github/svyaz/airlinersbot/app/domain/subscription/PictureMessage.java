package com.github.svyaz.airlinersbot.app.domain.subscription;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureMessage {

    private Long tlgUserId;

    private String languageCode;

    private Picture picture;
}
