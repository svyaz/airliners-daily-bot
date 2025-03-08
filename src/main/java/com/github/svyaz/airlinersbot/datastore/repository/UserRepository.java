package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByTlgUserId(Long tlgUserId);
}
