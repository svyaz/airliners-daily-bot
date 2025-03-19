package com.github.svyaz.airlinersbot.app.service.handler;

import com.github.svyaz.airlinersbot.app.domain.subscription.Subscription;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.request.RequestType;
import com.github.svyaz.airlinersbot.app.domain.response.TextResponse;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus.*;
import static com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType.*;

@Service
public class SubscribeTopHandlerBean extends AbstractRequestHandler<TextResponse> {

    @Override
    public RequestType myType() {
        return RequestType.SUBSCRIBE_TOP;
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

        if (INACTIVE.equals(subscription.getStatus())) {
            subscription.setStatus(ACTIVE);
            user.getSubscriptions().put(TOP, subscription);
            msgCode = "subscription.top.subscribe";
        } else {
            msgCode = "subscription.top.already-subscribed";
        }

        return new TextResponse(
                user.getTlgUserId(),
                messageService.getLocalizedMessage(msgCode),
                List.of(
                        List.of(getTopButton())
                )
        );
    }
}
