package com.github.svyaz.airlinersbot.app.service.user;

public interface UsersRequestsInProgressHolder {

    void add(Long userId);

    void remove(Long userId);

    boolean contains(Long userId);
}
