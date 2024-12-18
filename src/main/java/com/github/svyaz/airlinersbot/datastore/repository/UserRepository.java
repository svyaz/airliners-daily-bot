package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
