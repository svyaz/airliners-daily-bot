package com.github.svyaz.airlinersbot.app.service.user;

import com.github.svyaz.airlinersbot.app.domain.User;

public interface UserService {

    User findAndUpdate(User user);

    User save(User user);
}
