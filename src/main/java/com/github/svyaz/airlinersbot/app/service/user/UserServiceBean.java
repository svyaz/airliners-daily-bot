package com.github.svyaz.airlinersbot.app.service.user;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionStatus;
import com.github.svyaz.airlinersbot.app.domain.subscription.SubscriptionType;
import com.github.svyaz.airlinersbot.datastore.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        return save(updatedUser);
    }

    @Override
    public User save(User user) {
        return userStorageService.save(user);
    }

    @Override
    public List<User> getUsersWithTopSubscription() {
        return userStorageService.getWithSubscriptions(
                SubscriptionType.TOP,
                SubscriptionStatus.ACTIVE
        );
    }


}
