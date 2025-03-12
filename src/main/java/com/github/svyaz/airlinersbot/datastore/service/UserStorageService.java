package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;

import java.util.List;
import java.util.Optional;

public interface UserStorageService {

    Optional<User> findByTlgUserId(Long tlgUserId);

    User save(User user);

    List<User> getWithSubscriptions(SubscriptionType type, SubscriptionStatus status);
}
