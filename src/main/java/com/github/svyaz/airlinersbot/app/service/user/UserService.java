package com.github.svyaz.airlinersbot.app.service.user;

import com.github.svyaz.airlinersbot.app.domain.User;

import java.util.List;

public interface UserService {

    User findAndUpdate(User user);

    User save(User user);

    List<User> getUsersWithTopSubscription();
}
