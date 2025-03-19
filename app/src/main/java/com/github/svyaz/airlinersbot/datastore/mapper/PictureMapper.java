package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.datastore.model.PictureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PictureMapper {

    Picture toPicture(PictureEntity pictureEntity);

    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    PictureEntity toPictureEntity(Picture picture);
}
