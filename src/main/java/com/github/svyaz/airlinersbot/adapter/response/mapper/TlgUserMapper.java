package com.github.svyaz.airlinersbot.adapter.response.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TlgUserMapper {

    @Mapping(target = "tlgUserId", source = "id")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registerTime", ignore = true)
    @Mapping(target = "searchResult", ignore = true)
    com.github.svyaz.airlinersbot.app.domain.User toUser(User tlgUser);
}
