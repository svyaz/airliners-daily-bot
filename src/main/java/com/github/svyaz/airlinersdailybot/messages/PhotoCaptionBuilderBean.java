package com.github.svyaz.airlinersdailybot.messages;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class PhotoCaptionBuilderBean implements PhotoCaptionBuilder {

    private static final String defaultLangCode = "ru";
    //private static final Locale defaultLocale = Locale.of("ru");

    private final MessageSource messageSource;

    @Override
    public String build(PictureEntity entity, String langCode) {
        var locale = Locale.of(langCode);
        return messageSource.getMessage("caption", entity.getCaptionArgs(), locale);
    }

    /*private Locale getLocale(String langCode) {
        return Optional.ofNullable(langCode)
                .map(Locale::of)
                .orElseGet(() -> Locale.of(defaultLangCode));
    }*/
}
