package com.github.svyaz.airlinersbot.adapter.dto.request;

import lombok.*;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
@RequiredArgsConstructor
public abstract class AbstractRequestDto {

    private final User user;

    private final Message message;

}
