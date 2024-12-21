package com.github.svyaz.airlinersbot.app.service.user;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceBean implements UserService {

    private final UserStorageService userStorageService;

    @Override
    public User apply(User user) {
        var updatedUser = userStorageService.find(user.getId())
                .orElseGet(() ->
                        User.builder()
                                .id(user.getId())
                                .registerTime(LocalDateTime.now())
                                .build()
                );

        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setUserName(user.getUserName());
        updatedUser.setLanguageCode(user.getLanguageCode());

        return userStorageService.save(updatedUser);
    }
}
