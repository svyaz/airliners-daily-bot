package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.Subscription;
import com.github.svyaz.airlinersbot.datastore.model.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {

    @Mapping(target = "userId", source = "id.userId")
    @Mapping(target = "type", source = "id.type")
    Subscription toSubscription(SubscriptionEntity entity);

    @Mapping(target = "id.userId", source = "userId")
    @Mapping(target = "id.type", source = "type")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    SubscriptionEntity toSubscriptionEntity(Subscription subscription);
}
