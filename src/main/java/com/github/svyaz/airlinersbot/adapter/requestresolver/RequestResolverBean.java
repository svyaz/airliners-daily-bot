package com.github.svyaz.airlinersbot.adapter.requestresolver;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

@Component
public class RequestResolverBean implements RequestResolver {

    @Override
    public Request apply(Update update) {
        // Resolve text commands
        if (update.hasMessage()) {
            var type = switch (update.getMessage().getText()) {
                case HELP_COMMAND -> RequestType.HELP;
                case SUBSCRIBE_TOP_COMMAND -> RequestType.SUBSCRIBE_TOP;
                case UNSUBSCRIBE_TOP_COMMAND -> RequestType.UNSUBSCRIBE_TOP;
                default -> RequestType.SEARCH;
            };

            var user = update.getMessage().getFrom();
            var message = update.getMessage();

            return new Request(type, user, message);
        }

        // Resolve callbacks
        if (update.hasCallbackQuery()) {
            var type = switch (update.getCallbackQuery().getData()) {
                case SHOW_TOP_CB_DATA -> RequestType.TOP;
                case SHOW_NEXT_CB_DATA -> RequestType.SEARCH_NEXT;
                default -> RequestType.UNKNOWN_COMMAND;
            };

            var user = update.getCallbackQuery().getFrom();
            var message = update.getCallbackQuery().getMessage();

            return new Request(type, user, message);
        }

        return new Request(RequestType.UNKNOWN_COMMAND, null, null);
    }
}
