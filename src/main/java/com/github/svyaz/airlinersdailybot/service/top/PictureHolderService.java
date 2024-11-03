package com.github.svyaz.airlinersdailybot.service.top;

import com.github.svyaz.airlinersdailybot.model.PictureEntity;
import org.telegram.telegrambots.meta.api.objects.InputFile;

public interface PictureHolderService {

    boolean hasChanged(Long id);

    void setEntity(PictureEntity pictureEntity);

    PictureEntity getEntity();

    void setFileId(String fileId);

    InputFile getInputFile();
}
