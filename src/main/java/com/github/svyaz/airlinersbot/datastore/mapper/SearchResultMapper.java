package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.datastore.model.SearchResultEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SearchResultMapper {

    SearchResult toSearchResult(SearchResultEntity entity);

    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    SearchResultEntity toSearchResultEntity(SearchResult searchResult);
}
