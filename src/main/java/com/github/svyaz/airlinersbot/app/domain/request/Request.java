package com.github.svyaz.airlinersbot.app.domain.request;

import com.github.svyaz.airlinersbot.app.domain.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public record Request(
    RequestType type,
    User user,
    Message message) {
}
