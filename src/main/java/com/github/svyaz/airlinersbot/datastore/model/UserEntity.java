package com.github.svyaz.airlinersbot.datastore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String languageCode;
    private LocalDateTime registerTime;
    private LocalDateTime lastVisitTime;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SearchResultEntity searchResult;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SubscriptionEntity> subscriptions;
}
