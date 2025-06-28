package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_NEXT_CB_DATA;

@Component
@Order(2)
@RequiredArgsConstructor
public class NextPageButtonSupplier implements ButtonSupplier {

    private final MessageService messageService;

    @Override
    public InlineButton getButton(Picture picture) {
        return Optional.ofNullable(picture.getNextPageUri())
                .map(uri -> new InlineButton(
                        SHOW_NEXT_CB_DATA,
                        messageService.getLocalizedMessage("button.search-next"))
                )
                .orElse(null);
    }
}
