package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.model.SearchResultEntity;
import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SearchResultMapper.class)
public interface UserMapper {

    @Mapping(source = "searchResultEntity", target = "searchResult", qualifiedByName = "mapSearchResult")
    User toUser(UserEntity userEntity);

    @Mapping(target = "lastVisitTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "searchResult", target = "searchResultEntity", qualifiedByName = "mapSearchResultEntity")
    UserEntity toUserEntity(User user);

    @Named("mapSearchResult")
    default SearchResult mapSearchResult(SearchResultEntity searchResultEntity) {
        if (searchResultEntity == null) {
            return null;
        }
        return Mappers.getMapper(SearchResultMapper.class).toSearchResult(searchResultEntity);
    }

    @Named("mapSearchResultEntity")
    default SearchResultEntity mapSearchResultEntity(SearchResult searchResult) {
        if (searchResult == null) {
            return null;
        }
        return Mappers.getMapper(SearchResultMapper.class).toSearchResultEntity(searchResult);
    }
}