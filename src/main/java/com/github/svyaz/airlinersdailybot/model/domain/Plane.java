package com.github.svyaz.airlinersdailybot.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Plane {

    private Long id;
    private String tlgFileId;
    private String fileUri;

    //todo Надо ли следующие свойства в отдельный Data-class выделять? Не уверен.
    private String pageUri;
    private String airline;
    private String aircraft;
    private String registration;
    private String location;
    private String date;
    private String content;
    private String author;
    private String authorCountry;

}
