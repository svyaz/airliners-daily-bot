package com.github.svyaz.airlinersbot.datastore.model;

import com.github.svyaz.airlinersbot.app.domain.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.SubscriptionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private SubscriptionType type;

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
