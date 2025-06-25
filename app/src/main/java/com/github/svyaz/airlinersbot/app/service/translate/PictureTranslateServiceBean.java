package com.github.svyaz.airlinersbot.app.service.translate;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PictureTranslateServiceBean implements PictureTranslateService {

    @Override
    public Picture apply(Picture picture, String s) {
        return null;
    }
}
