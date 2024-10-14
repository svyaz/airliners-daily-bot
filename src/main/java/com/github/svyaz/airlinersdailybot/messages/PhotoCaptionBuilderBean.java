package com.github.svyaz.airlinersdailybot.messages;

import com.github.svyaz.airlinersdailybot.conf.MessagesConfig;
import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PhotoCaptionBuilderBean implements PhotoCaptionBuilder {

    private final MessageSource messageSource;
    private final MessagesConfig messagesConfig;

    @Override
    public String build(PictureEntity entity, String langCode) {
        return messageSource.getMessage(
                "caption",
                entity.getCaptionArgs(),
                Locale.of(
                        Optional.ofNullable(langCode)
                                .orElseGet(messagesConfig::getDefaultLangCode)
                )
        );
    }

}
