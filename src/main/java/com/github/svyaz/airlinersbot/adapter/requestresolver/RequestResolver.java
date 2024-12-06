package com.github.svyaz.airlinersbot.adapter.requestresolver;

import com.github.svyaz.airlinersbot.adapter.dto.request.AbstractRequestDto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

public interface RequestResolver extends Function<Update, AbstractRequestDto>{
}
