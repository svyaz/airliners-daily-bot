package com.github.svyaz.airlinersdailybot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureData {

    // photo page uri
    private String photoPageUri;

    // airline
    private String airline;

    // aircraft
    private String aircraft;

    // actual registration
    private String registration;

    // photo location
    private String location;

    // photo date
    private String date;

    // content
    private String content;

    // photographer
    private String author;

    // author country
    private String authorCountry;

}
