package com.github.svyaz.airlinersdailybot.service;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PictureHolderServiceBean implements PictureHolderService {

    private PictureEntity pictureEntity;

    @Override
    @Synchronized
    public boolean hasChanged(Long id) {
        log.info("hasChanged <- id [{}]", id);

        return Optional.ofNullable(pictureEntity)
                .map(PictureEntity::getId)
                .filter(entityId -> Objects.equals(entityId, id))
                .isEmpty();
    }

    @Override
    @Synchronized
    public void setEntity(PictureEntity pictureEntity) {
        this.pictureEntity = pictureEntity;
    }

    @Override
    @Synchronized
    public PictureEntity getEntity() {
        return pictureEntity;
    }

    @Override
    @Synchronized
    public void setFileId(String fileId) {
        this.pictureEntity.setFileId(fileId);
    }

    @Override
    @Synchronized
    public InputFile getInputFile() {
        var fileId = Optional.ofNullable(pictureEntity.getFileId())
                .orElseGet(() -> pictureEntity.getPhotoFileUri());
        return new InputFile(fileId);
    }
}
