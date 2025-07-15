package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

@Component
@Order(2)
@RequiredArgsConstructor
public class ContentButtonSupplier implements ButtonSupplier {

    private final MessageService messageService;

    @Override
    public InlineButton getButton(Picture picture, User user) {
        return Optional.ofNullable(picture)
                .map(Picture::getContent)
                .map(content -> new InlineButton(
                        String.format("%s-%d", SHOW_CONTENT_CB_DATA, picture.getId()),
                        messageService.getLocalizedMessage("button.content"))
                )
                .orElse(null);
    }
}
