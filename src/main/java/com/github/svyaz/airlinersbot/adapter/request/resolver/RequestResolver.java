package com.github.svyaz.airlinersbot.adapter.request.resolver;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

public interface RequestResolver extends Function<Update, Request>{
}
