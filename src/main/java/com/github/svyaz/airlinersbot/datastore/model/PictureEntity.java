package com.github.svyaz.airlinersbot.datastore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    /**
     * photo file uri
     */
    private String photoFileUri;

    /**
     * next file uri
     */
    private String nextPageUri;

    /**
     * Picture data next
     */
    private String photoPageUri;

    private String airline;

    private String aircraft;

    private String registration;

    private String location;

    private String date;

    private String content;

    private String author;

    private String authorCountry;

    private LocalDateTime updateTime; //todo index

}
