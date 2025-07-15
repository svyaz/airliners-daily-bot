package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;

import java.util.List;

public interface ButtonsService {

    List<List<InlineButton>> getButtons(Picture picture, User user);

    default List<List<InlineButton>> getButtons() {
        return getButtons(null, null);
    }
}
