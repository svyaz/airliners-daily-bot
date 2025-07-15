package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

@Component
@Order(1)
@RequiredArgsConstructor
public class TopButtonSupplier implements ButtonSupplier {

    private final MessageService messageService;

    @Override
    public InlineButton getButton(Picture picture, User user) {
        return new InlineButton(
                SHOW_TOP_CB_DATA,
                messageService.getLocalizedMessage("button.show-top")
        );
    }
}
