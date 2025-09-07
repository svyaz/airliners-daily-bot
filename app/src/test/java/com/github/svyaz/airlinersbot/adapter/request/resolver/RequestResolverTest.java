package com.github.svyaz.airlinersbot.adapter.request.resolver;

import com.github.svyaz.airlinersbot.SpringBootTestSpec;
import com.github.svyaz.airlinersbot.adapter.response.mapper.TlgUserMapper;
import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.stream.Stream;

import static com.github.svyaz.airlinersbot.conf.properties.Constants.*;

public class RequestResolverTest extends SpringBootTestSpec {

    private static final User tlgUser = new User(1L, "Sonny", false);

    @Autowired
    RequestResolver requestResolver;

    @Autowired
    TlgUserMapper userMapper;

    @ParameterizedTest(name = "MessageText: {0} => RequestType: {1}")
    @MethodSource(value = {"textCommandsData"})
    void resolve_text_command(String messageText, RequestType requestType) {
        var message = new Message();
        message.setText(messageText);
        message.setFrom(tlgUser);

        var update = new Update();
        update.setMessage(message);

        Assertions.assertEquals(
                new Request(requestType, userMapper.toUser(tlgUser), message, messageText),
                requestResolver.apply(update)
        );
    }

    static Stream<Arguments> textCommandsData() {
        return Stream.of(
                Arguments.of(START_COMMAND, RequestType.START),
                Arguments.of(HELP_COMMAND, RequestType.HELP),
                Arguments.of(SUBSCRIBE_TOP_COMMAND, RequestType.SUBSCRIBE_TOP),
                Arguments.of(UNSUBSCRIBE_TOP_COMMAND, RequestType.UNSUBSCRIBE_TOP),
                Arguments.of("s7", RequestType.SEARCH),
                Arguments.of("73404", RequestType.SEARCH),
                Arguments.of("Самолётик", RequestType.SEARCH)
        );
    }

    @ParameterizedTest(name = "CallbackData: {0} => RequestType: {1}")
    @MethodSource(value = {"callbacksData"})
    void resolve_callback_command(String callbackData, RequestType requestType) {
        var callbackQuery = new CallbackQuery();
        callbackQuery.setData(callbackData);
        callbackQuery.setFrom(tlgUser);
        callbackQuery.setMessage(new Message());

        var update = new Update();
        update.setCallbackQuery(callbackQuery);

        Assertions.assertEquals(
                new Request(requestType, userMapper.toUser(tlgUser), callbackQuery.getMessage(), callbackData),
                requestResolver.apply(update)
        );
    }

    static Stream<Arguments> callbacksData() {
        return Stream.of(
                Arguments.of(SHOW_TOP_CB_DATA, RequestType.TOP),
                Arguments.of(SHOW_NEXT_CB_DATA, RequestType.SEARCH_NEXT),
                Arguments.of(SHOW_CONTENT_CB_DATA + "-123", RequestType.CONTENT)
        );
    }

    @Test
    void unknown_command() {
        var message = new Message();
        message.setText("/unknown");
        message.setFrom(tlgUser);

        var update = new Update();
        update.setMessage(message);

        Assertions.assertEquals(
                new Request(RequestType.UNKNOWN_COMMAND, userMapper.toUser(tlgUser), message, "/unknown"),
                requestResolver.apply(update)
        );
    }

}
