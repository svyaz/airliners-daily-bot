package com.github.svyaz.airlinersbot.app.domain.request;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public record Request(
    RequestType type,
    User user,
    Message message) {
}
