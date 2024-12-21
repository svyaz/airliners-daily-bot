package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.datastore.mapper.PictureMapper;
import com.github.svyaz.airlinersbot.datastore.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PictureStorageServiceBean implements PictureStorageService {

    private final PictureRepository repository;

    private final PictureMapper mapper;

    @Override
    public Optional<Picture> find(Long id) {
        return repository.findById(id)
                .map(mapper::toPicture);
    }

    @Override
    public Picture save(Picture picture) {
        return Optional.of(picture)
                .map(mapper::toPictureEntity)
                .map(repository::saveAndFlush)
                .map(mapper::toPicture)
                .orElse(null);
    }

}
