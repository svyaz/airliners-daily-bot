package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.request.Request;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.domain.subscription.Subscription;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import com.github.svyaz.airlinersbot.conf.properties.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumMap;
import java.util.List;

import static org.mockito.Mockito.when;

public class UnsubscribeTopHandlerTest extends HandlersSpec {

    private static final String UNSUBSCRIBE_TEXT = "Unsubscribe text";
    private static final String ALREADY_UNSUBSCRIBED_TEXT = "Already unsubscribed text";

    @Autowired
    UnsubscribeTopHandlerBean handler;

    @Test
    void handle_when_user_have_no_subscriptions_successful() {
        user.setSubscriptions(null);

        Request request = new Request(
                RequestType.UNSUBSCRIBE_TOP,
                user,
                new Message(),
                Constants.UNSUBSCRIBE_TOP_COMMAND
        );

        when(messageService.getLocalizedMessage("subscription.top.not-subscribed"))
                .thenReturn(ALREADY_UNSUBSCRIBED_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new TextResponse(1L, ALREADY_UNSUBSCRIBED_TEXT, List.of())),
                handler.handle(request)
        );

        Assertions.assertEquals(
                user.getSubscriptions().get(SubscriptionType.TOP).getStatus(),
                SubscriptionStatus.INACTIVE
        );
    }

    @Test
    void handle_when_user_have_INACTIVE_subscription_status_successful() {
        EnumMap<SubscriptionType, Subscription> subscriptions = new EnumMap<>(SubscriptionType.class);

        subscriptions.put(
                SubscriptionType.TOP,
                new Subscription(user.getId(), SubscriptionType.TOP, SubscriptionStatus.INACTIVE)
        );

        user.setSubscriptions(subscriptions);

        Request request = new Request(
                RequestType.UNSUBSCRIBE_TOP,
                user,
                new Message(),
                Constants.UNSUBSCRIBE_TOP_COMMAND
        );

        when(messageService.getLocalizedMessage("subscription.top.not-subscribed"))
                .thenReturn(ALREADY_UNSUBSCRIBED_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new TextResponse(1L, ALREADY_UNSUBSCRIBED_TEXT, List.of())),
                handler.handle(request)
        );

        Assertions.assertEquals(
                user.getSubscriptions().get(SubscriptionType.TOP).getStatus(),
                SubscriptionStatus.INACTIVE
        );
    }

    @Test
    void handle_when_user_have_ACTIVE_subscription_status_successful() {
        EnumMap<SubscriptionType, Subscription> subscriptions = new EnumMap<>(SubscriptionType.class);

        subscriptions.put(
                SubscriptionType.TOP,
                new Subscription(user.getId(), SubscriptionType.TOP, SubscriptionStatus.ACTIVE)
        );

        user.setSubscriptions(subscriptions);

        Request request = new Request(
                RequestType.UNSUBSCRIBE_TOP,
                user,
                new Message(),
                Constants.SUBSCRIBE_TOP_COMMAND
        );

        when(messageService.getLocalizedMessage("subscription.top.unsubscribe"))
                .thenReturn(UNSUBSCRIBE_TEXT);

        when(buttonsService.getButtons())
                .thenReturn(List.of());

        Assertions.assertEquals(
                List.of(new TextResponse(1L, UNSUBSCRIBE_TEXT, List.of())),
                handler.handle(request)
        );

        Assertions.assertEquals(
                user.getSubscriptions().get(SubscriptionType.TOP).getStatus(),
                SubscriptionStatus.INACTIVE
        );
    }

    @Test
    void myType_returns_UNSUBSCRIBE_TOP() {
        Assertions.assertEquals(
                RequestType.UNSUBSCRIBE_TOP,
                handler.myType()
        );
    }
}
