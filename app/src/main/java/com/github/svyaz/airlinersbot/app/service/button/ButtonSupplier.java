package com.github.svyaz.airlinersbot.app.service.button;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;

public interface ButtonSupplier {

    InlineButton getButton(Picture picture, User user);
}
