package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
}
