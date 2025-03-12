package com.github.svyaz.airlinersbot.app.domain;

import com.github.svyaz.airlinersbot.app.domain.subscription.Subscription;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.EnumMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private Long tlgUserId;
    private String userName;
    private String firstName;
    private String lastName;
    private String languageCode;
    private LocalDateTime registerTime;
    private SearchResult searchResult;
    private EnumMap<SubscriptionType, Subscription> subscriptions;
}
