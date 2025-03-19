package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.Picture;

import java.util.Optional;

public interface PictureStorageService {

    Optional<Picture> find(Long id);

    Picture save(Picture picture);

    Optional<Picture> getTop();
}
