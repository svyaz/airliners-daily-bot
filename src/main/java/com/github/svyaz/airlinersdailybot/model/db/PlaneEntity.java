package com.github.svyaz.airlinersdailybot.model.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Plane")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaneEntity {

    private Long id;
    private Long internalId;

    private String tlgFileId;
    private String fileUri;

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
