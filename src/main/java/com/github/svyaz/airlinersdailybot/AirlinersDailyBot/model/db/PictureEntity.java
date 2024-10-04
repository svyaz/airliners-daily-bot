package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureEntity {

    // picture title
    private String title;

    // link
    private String photoLink;

    // airline
    private String airline;

    // aircraft
    private String aircraft;

    // actual registration
    private String registration;

    // manufacturer serial number
    private String msn;

    // photo location
    private String location;

    // photo date
    private LocalDate date;

    // content
    private String content;

    // photographer
    private String author;

    // author country
    private String authorCountry;
}
