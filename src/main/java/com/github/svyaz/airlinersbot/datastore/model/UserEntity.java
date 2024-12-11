package com.github.svyaz.airlinersbot.datastore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    private Long id;        // primary key
    private Long chatId;    // unique key
    private String username;
    private String firstName;
    private String lastName;
    private LocalDateTime regTime;
    private LocalDateTime lastVisitTime;
}
