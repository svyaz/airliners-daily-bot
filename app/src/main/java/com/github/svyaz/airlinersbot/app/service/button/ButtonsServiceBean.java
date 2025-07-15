package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ButtonsServiceBean implements ButtonsService {

    private final List<ButtonSupplier> buttonSuppliers;

    @Override
    public List<List<InlineButton>> getButtons(Picture picture, User user) {
        return List.of(
                buttonSuppliers.stream()
                        .map(s -> s.getButton(picture, user))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
}
