package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.datastore.model.SearchResultEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchResultMapper {

    @Mapping(target = "picture", expression = "java(new com.github.svyaz.airlinersbot.app.domain.Picture(entity.getPictureUri(), entity.getNextPictureUri()))")
    SearchResult toSearchResult(SearchResultEntity entity);

    @Mapping(target = "pictureUri", expression = "java(searchResult.getPicture().getPhotoFileUri())")
    @Mapping(target = "nextPictureUri", expression = "java(searchResult.getPicture().getNextPageUri())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    SearchResultEntity toSearchResultEntity(SearchResult searchResult);
}
