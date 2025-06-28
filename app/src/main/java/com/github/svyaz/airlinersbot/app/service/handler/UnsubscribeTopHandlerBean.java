package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.subscription.Subscription;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus.ACTIVE;
import static com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus.INACTIVE;
import static com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType.TOP;

@Service
public class UnsubscribeTopHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.UNSUBSCRIBE_TOP;
    }

    @Override
    TextResponse getResponse(User user, Message message) {
        if (Objects.isNull(user.getSubscriptions())) {
            user.setSubscriptions(new EnumMap<>(SubscriptionType.class));
        }

        var subscription = Optional.ofNullable(user.getSubscriptions())
                .map(subs -> subs.get(TOP))
                .orElseGet(() -> new Subscription(user.getId(), TOP, INACTIVE));

        String msgCode;

        if (ACTIVE.equals(subscription.getStatus())) {
            subscription.setStatus(INACTIVE);
            user.getSubscriptions().put(TOP, subscription);
            msgCode = "subscription.top.unsubscribe";
        } else {
            msgCode = "subscription.top.not-subscribed";
        }

        return new TextResponse(
                user.getTlgUserId(),
                messageService.getLocalizedMessage(msgCode),
                buttonsService.getButtons()
        );
    }
}
