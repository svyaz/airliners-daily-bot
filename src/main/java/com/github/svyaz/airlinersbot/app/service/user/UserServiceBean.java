package com.github.svyaz.airlinersbot.app.service.user;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceBean implements UserService {

    private final UserStorageService userStorageService;

    @Override
    public User findAndUpdate(User user) {
        User updatedUser = userStorageService.findByTlgUserId(user.getTlgUserId())
                .orElseGet(() ->
                        User.builder()
                                .tlgUserId(user.getTlgUserId())
                                .registerTime(LocalDateTime.now())
                                .build()
                );

        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setUserName(user.getUserName());
        updatedUser.setLanguageCode(user.getLanguageCode());
        return updatedUser;
    }

    @Override
    public User save(User user) {
        return userStorageService.save(user);
    }
}
