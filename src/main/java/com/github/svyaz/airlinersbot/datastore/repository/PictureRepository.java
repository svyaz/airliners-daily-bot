package com.github.svyaz.airlinersbot.datastore.repository;

import com.github.svyaz.airlinersbot.datastore.model.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<PictureEntity, Long> {
}
