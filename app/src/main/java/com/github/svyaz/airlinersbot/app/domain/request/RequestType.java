package com.github.svyaz.airlinersbot.app.domain.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.function.Predicate;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

/**
 * TODO :  Enum elements order matters? Check in tests
 */
@Getter
@RequiredArgsConstructor
public enum RequestType {
    START(UpdateType.COMMAND, START_COMMAND::equals),
    HELP(UpdateType.COMMAND, HELP_COMMAND::equals),
    SUBSCRIBE_TOP(UpdateType.COMMAND, SUBSCRIBE_TOP_COMMAND::equals),
    UNSUBSCRIBE_TOP(UpdateType.COMMAND, UNSUBSCRIBE_TOP_COMMAND::equals),
    SEARCH(UpdateType.COMMAND, text -> !text.startsWith("/") && text.length() > 2),

    SEARCH_NEXT(UpdateType.CALLBACK_BUTTON, SHOW_NEXT_CB_DATA::equals),
    TOP(UpdateType.CALLBACK_BUTTON, SHOW_TOP_CB_DATA::equals),
    CONTENT(UpdateType.CALLBACK_BUTTON, text -> text.matches(String.format("%s_\\d+", SHOW_CONTENT_CB_DATA))),
    UNKNOWN_COMMAND(UpdateType.UNKNOWN, null);

    final UpdateType updateType;
    final Predicate<String> filter;

}
