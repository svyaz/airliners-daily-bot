package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.mapper.UserMapper;
import com.github.svyaz.airlinersbot.datastore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStorageServiceBean implements UserStorageService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findUser(Long id) {
        return repository.findById(id)
                .map(mapper::toUser);
    }

    @Override
    public void save(User user) {
        repository.saveAndFlush(
                mapper.toUserEntity(user)
        );
    }
}
