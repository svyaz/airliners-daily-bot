package com.github.svyaz.airlinersbot.datastore.mapper;

import com.github.svyaz.airlinersbot.app.domain.Subscription;
import com.github.svyaz.airlinersbot.datastore.model.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {

    //@Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id.userId", target = "userId")
    @Mapping(source = "id.type", target = "type")
    Subscription toSubscription(SubscriptionEntity entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id.userId", source = "userId")
    @Mapping(target = "id.type", source = "type")
    SubscriptionEntity toSubscriptionEntity(Subscription subscription);
}
