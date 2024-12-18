package com.github.svyaz.airlinersbot.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String languageCode;
    private LocalDateTime registerTime;
}
