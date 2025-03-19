package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import com.github.svyaz.airlinersbot.datastore.mapper.UserMapper;
import com.github.svyaz.airlinersbot.datastore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStorageServiceBean implements UserStorageService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Override
    public Optional<User> findByTlgUserId(Long tlgUserId) {
        return repository.findByTlgUserId(tlgUserId)
                .map(mapper::toUser);
    }

    @Override
    public User save(User user) {
        return Optional.of(user)
                .map(mapper::toUserEntity)
                .map(repository::saveAndFlush)
                .map(mapper::toUser)
                .orElse(null);
    }

    @Override
    public List<User> getWithSubscriptions(SubscriptionType type, SubscriptionStatus status) {
        return repository.findBySubscriptionTypeAndStatus(type, status)
                .stream()
                .map(mapper::toUser)
                .collect(Collectors.toList());
    }
}
