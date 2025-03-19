package com.github.svyaz.airlinersbot.adapter.response.mapper.keyboard;

import com.github.svyaz.airlinersbot.app.domain.response.InlineButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;
import java.util.function.Function;

public interface KeyboardMapper extends Function<List<List<InlineButton>>, ReplyKeyboard> {
}
