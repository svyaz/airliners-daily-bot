package com.github.svyaz.airlinersbot.datastore.model;

import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
@EqualsAndHashCode(exclude = {"updateTime"})
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionId id;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private LocalDateTime updateTime;

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
