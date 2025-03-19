package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByTlgUserId(Long tlgUserId);

    @Query("SELECT u FROM UserEntity u JOIN u.subscriptions s WHERE s.id.type = :type AND s.status = :status")
    List<UserEntity> findBySubscriptionTypeAndStatus(@Param("type") SubscriptionType type, @Param("status") SubscriptionStatus status);
}
