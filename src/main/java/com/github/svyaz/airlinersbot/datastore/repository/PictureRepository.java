package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    Optional<PictureEntity> findTopByOrderByUpdateTimeDesc();
}
