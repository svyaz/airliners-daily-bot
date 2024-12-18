package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.User;

import java.util.Optional;

public interface UserStorageService {

    Optional<User> findUser(Long id);

    void save(User user);
}
