package com.github.svyaz.airlinersbot.adapter.request.resolver;

import com.github.svyaz.airlinersbot.adapter.response.mapper.TlgUserMapper;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

@Component
@RequiredArgsConstructor
public class RequestResolverBean implements RequestResolver {

    private final TlgUserMapper userMapper;

    @Override
    public Request apply(Update update) {
        // Resolve text commands
        if (update.hasMessage()) {
            return new Request(
                    getTextCommandType(update),
                    userMapper.toUser(update.getMessage().getFrom()),
                    update.getMessage()
            );
        }

        // Resolve callbacks
        if (update.hasCallbackQuery()) {
            return new Request(
                    getCallbackCommandType(update),
                    userMapper.toUser(update.getCallbackQuery().getFrom()),
                    update.getCallbackQuery().getMessage()
            );
        }

        return new Request(RequestType.UNKNOWN_COMMAND, null, null);
    }

    private RequestType getTextCommandType(Update update) {
        return switch (update.getMessage().getText()) {
            case START_COMMAND -> RequestType.START;
            case HELP_COMMAND -> RequestType.HELP;
            case SUBSCRIBE_TOP_COMMAND -> RequestType.SUBSCRIBE_TOP;
            case UNSUBSCRIBE_TOP_COMMAND -> RequestType.UNSUBSCRIBE_TOP;
            default -> RequestType.SEARCH;
        };
    }

    private RequestType getCallbackCommandType(Update update) {
        return switch (update.getCallbackQuery().getData()) {
            case SHOW_TOP_CB_DATA -> RequestType.TOP;
            case SHOW_NEXT_CB_DATA -> RequestType.SEARCH_NEXT;
            default -> RequestType.UNKNOWN_COMMAND;
        };
    }

}
