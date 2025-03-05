package com.github.svyaz.airlinersbot.app.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    private Long userId;
    private SubscriptionType type;
    private SubscriptionStatus status;
    private LocalDateTime updateTime;
}
