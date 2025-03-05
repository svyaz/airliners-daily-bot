package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.Subscription;
import com.github.svyaz.airlinersbot.app.domain.SubscriptionType;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.model.SearchResultEntity;
import com.github.svyaz.airlinersbot.datastore.model.SubscriptionEntity;
import com.github.svyaz.airlinersbot.datastore.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SearchResultMapper.class, SubscriptionMapper.class}
)
public interface UserMapper {

    @Mapping(source = "searchResult", target = "searchResult", qualifiedByName = "mapSearchResult")
    @Mapping(source = "subscriptions", target = "subscriptions", qualifiedByName = "mapSubscriptions")
    User toUser(UserEntity user);

    @Mapping(target = "lastVisitTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "searchResult", target = "searchResult", qualifiedByName = "mapSearchResultEntity")
    @Mapping(source = "subscriptions", target = "subscriptions", qualifiedByName = "mapSubscriptionEntities")
    UserEntity toUserEntity(User user);

    @Named("mapSearchResult")
    default SearchResult mapSearchResult(SearchResultEntity searchResult) {
        if (searchResult == null) {
            return null;
        }
        return Mappers.getMapper(SearchResultMapper.class).toSearchResult(searchResult);
    }

    @Named("mapSearchResultEntity")
    default SearchResultEntity mapSearchResultEntity(SearchResult searchResult) {
        if (searchResult == null) {
            return null;
        }
        return Mappers.getMapper(SearchResultMapper.class).toSearchResultEntity(searchResult);
    }

    @Named("mapSubscriptions")
    default EnumMap<SubscriptionType, Subscription> mapSubscriptions(Set<SubscriptionEntity> subscriptions) {
        if (subscriptions == null) {
            return null;
        }
        return subscriptions.stream()
                .map(Mappers.getMapper(SubscriptionMapper.class)::toSubscription)
                .collect(
                        Collectors.toMap(
                                Subscription::getType,
                                Function.identity(),
                                (existing, replacement) -> existing,
                                () -> new EnumMap<>(SubscriptionType.class)
                        )
                );
    }

    @Named("mapSubscriptionEntities")
    default Set<SubscriptionEntity> mapSubscriptionEntities(EnumMap<SubscriptionType, Subscription> subscriptions) {
        if (subscriptions == null) {
            return null;
        }
        return subscriptions.values().stream()
                .map(Mappers.getMapper(SubscriptionMapper.class)::toSubscriptionEntity)
                .collect(Collectors.toSet());
    }
}