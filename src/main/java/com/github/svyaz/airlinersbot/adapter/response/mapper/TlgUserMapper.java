package com.github.svyaz.airlinersbot.adapter.response.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TlgUserMapper {

    com.github.svyaz.airlinersbot.app.domain.User toUser(User tlgUser);
}
