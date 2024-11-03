package com.github.svyaz.airlinersdailybot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureEntity {

    // Airliners.net identifier
    private Long id;

    // photo file uri
    private String photoFileUri;

    // next file uri
    private String nextPageUri;

    // photo id, if it has been already uploaded to telegram server
    private String fileId;

    // picture content to form message caption
    private PictureData pictureData;

    public Object[] getCaptionArgs() {
        return new Object[]{
                // title with link
                Optional.ofNullable(pictureData.getPhotoPageUri())
                        .map(u -> String.format("<a href='%s'>%d</a>", u, id))
                        .orElseGet(() -> String.format("%d", id)),
                // airline
                Optional.ofNullable(pictureData.getAirline()).orElse("-"),
                // aircraft
                Optional.ofNullable(pictureData.getAircraft()).orElse("-"),
                // registration
                Optional.ofNullable(pictureData.getRegistration()).orElse("-"),
                // location
                Optional.ofNullable(pictureData.getLocation()).orElse("-"),
                // date
                Optional.ofNullable(pictureData.getDate()).orElse("-"),
                // author
                Optional.ofNullable(pictureData.getAuthor()).orElse("-"),
                // author country
                Optional.ofNullable(pictureData.getAuthorCountry())
                        .map(ac -> String.format("(%s)",ac))
                        .orElse("")
        };
    }
}
