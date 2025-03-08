package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.User;

import java.util.Optional;

public interface UserStorageService {

    Optional<User> findByTlgUserId(Long tlgUserId);

    User save(User user);
}
