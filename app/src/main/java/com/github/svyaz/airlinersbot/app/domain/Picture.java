package com.github.svyaz.airlinersbot.app.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Picture {
    private Long id;
    private String photoFileUri;
    private String nextPageUri;
    private String photoPageUri;
    private String airline;
    private String aircraft;
    private String registration;
    @Translatable private String location;
    private String date;
    @Translatable private String content;
    private String author;
    private String authorCountry;
    private LocalDateTime updateTime;

    public Picture(String photoFileUri, String nextPageUri) {
        this.photoFileUri = photoFileUri;
        this.nextPageUri = nextPageUri;
    }

    //todo : сделать фильтрацию в парсере чтобы вместо "-" и пустых строк был null.
    public Object[] getCaptionArgs() {
        return new Object[]{
                // title with link
                Optional.ofNullable(photoPageUri)
                        .map(u -> String.format("<a href='%s'>%d</a>", u, id))
                        .orElseGet(() -> String.format("%d", id)),
                // airline
                Optional.ofNullable(airline).orElse("-"),
                // aircraft
                Optional.ofNullable(aircraft).orElse("-"),
                // registration
                Optional.ofNullable(registration).orElse("-"),
                // location
                Optional.ofNullable(location).orElse("-"),
                // date
                Optional.ofNullable(date).orElse("-"),
                // author
                Optional.ofNullable(author).orElse("-"),
                // author country
                Optional.ofNullable(authorCountry)
                        .map(ac -> String.format("(%s)", ac))
                        .orElse("")
        };
    }
}
