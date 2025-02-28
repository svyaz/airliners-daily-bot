package com.github.svyaz.airlinersbot.app.service.user;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class UsersRequestsInProgressHolderBean implements UsersRequestsInProgressHolder {

    private final Set<Long> usersInProgress;

    public UsersRequestsInProgressHolderBean() {
        this.usersInProgress = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public void add(Long userId) {
        usersInProgress.add(userId);
    }

    @Override
    public void remove(Long userId) {
        usersInProgress.remove(userId);
    }

    @Override
    public boolean contains(Long userId) {
        return usersInProgress.contains(userId);
    }
}
