package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import com.github.svyaz.airlinersbot.app.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.SHOW_TOP_CB_DATA;

@Component
@RequiredArgsConstructor
public class TopButtonSupplier implements ButtonSupplier {

    private final MessageService messageService;

    @Override
    public InlineButton getButton() {
        return new InlineButton(
                SHOW_TOP_CB_DATA,
                messageService.getLocalizedMessage("button.show-top")
        );
    }
}
