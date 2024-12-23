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
public class Picture {
    private Long id;
    private String photoFileUri;
    private String nextPageUri;
    private String photoPageUri;
    private String airline;
    private String aircraft;
    private String registration;
    private String location;
    private String date;
    private String content;
    private String author;
    private String authorCountry;
    private LocalDateTime updateTime;
}
