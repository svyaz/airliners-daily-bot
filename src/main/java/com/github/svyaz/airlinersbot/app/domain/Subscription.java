package com.github.svyaz.airlinersbot.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    private Long userId;
    private SubscriptionType type;
    private SubscriptionStatus status;
}
