package com.github.svyaz.airlinersbot.app.service.message;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceBean implements MessageService {

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @Override
    public String getFullPictureCaption(Picture picture) {
        return "%s%s".formatted(
                getLocalizedMessage("photo.caption", picture.getCaptionArgs()),
                Optional.of(picture)
                        .filter(p -> Objects.nonNull(p.getContent()))
                        .map(c -> getLocalizedMessage("photo.details", c.getDetailsArgs()))
                        .orElse("")
        );
    }

}
