package com.github.svyaz.airlinersbot.datastore.model;

import com.github.svyaz.airlinersbot.app.domain.PictureType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "picture")
public class PictureEntity {

    /**
     * Airliners.net identifier
     */
    @Id
    private Long id;

    private String photoFileUri;

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

    @Enumerated(EnumType.STRING)
    private PictureType pictureType;

}
