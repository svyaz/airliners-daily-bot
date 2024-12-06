package com.github.svyaz.airlinersbot.adapter.requestresolver;

import com.github.svyaz.airlinersbot.adapter.dto.request.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

@Component
public class RequestResolverBean implements RequestResolver {

    @Override
    public AbstractRequestDto apply(Update update) {
        // Resolve text commands
        if (update.hasMessage()) {
            return switch (update.getMessage().getText()) {
                case HELP_COMMAND -> new HelpRequestDto(update);
                case SUBSCRIBE_TOP_COMMAND -> new SubscribeTopRequestDto(update);
                case UNSUBSCRIBE_TOP_COMMAND -> new UnsubscribeTopRequestDto(update);
                default -> new SearchRequestDto(update);
            };
        }

        // Resolve callbacks
        if (update.hasCallbackQuery()) {
            return switch (update.getCallbackQuery().getData()) {
                case SHOW_TOP_CB_DATA -> new TopRequestDto(update);
                case SHOW_NEXT_CB_DATA -> new SearchNextRequestDto(update);
                default -> new UnknownCommandRequestDto(update);
            };
        }

        return new UnknownCommandRequestDto(update);
    }
}
