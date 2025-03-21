package com.github.svyaz.airlinersbot.datastore.model;

import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionId id;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class SubscriptionId implements Serializable {
        @Column(name = "user_id", nullable = false, insertable=false, updatable=false)
        private Long userId;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false, insertable=false, updatable=false)
        private SubscriptionType type;
    }
}
