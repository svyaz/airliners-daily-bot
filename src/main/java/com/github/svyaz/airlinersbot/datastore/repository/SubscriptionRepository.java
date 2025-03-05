package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, SubscriptionEntity.SubscriptionId> {
}
