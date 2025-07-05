package com.github.svyaz.airlinersbot.adapter.request.resolver;

import com.github.svyaz.airlinersbot.adapter.response.mapper.TlgUserMapper;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.request.UpdateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;

import static com.github.svyaz.airlinersbot.app.domain.request.RequestType.UNKNOWN_COMMAND;

@Component
@RequiredArgsConstructor
public class RequestResolverBean implements RequestResolver {

    private final TlgUserMapper userMapper;

    @Override
    public Request apply(Update update) {
        return Arrays.stream(UpdateType.values())
                .filter(updateType -> updateType.getFilter().test(update))
                .findFirst()
                .map(updateType -> {
                            var testText = updateType.getTestTextGetter().apply(update);
                            var tlgUser = updateType.getTlgUserGetter().apply(update);
                            var tlgMessage = updateType.getMessageGetter().apply(update);

                            return Arrays.stream(RequestType.values())
                                    .filter(requestType -> requestType.getUpdateType().equals(updateType))
                                    .filter(requestType -> requestType.getFilter().test(testText))
                                    .findFirst()
                                    .map(requestType ->
                                            new Request(
                                                    requestType,
                                                    userMapper.toUser(tlgUser),
                                                    tlgMessage,
                                                    testText
                                            )
                                    )
                                    .orElse(null);
                        }
                )
                .orElseGet(() -> new Request(UNKNOWN_COMMAND, null, null, null));
    }

}
