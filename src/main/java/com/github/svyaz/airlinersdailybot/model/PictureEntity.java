package com.github.svyaz.airlinersdailybot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PictureEntity {

    // Airliners.net identifier
    private Long id;

    // photo file uri
    private String photoFileUri;

    // photo id, if it has been already uploaded to telegram server
    private String fileId;

    // picture content to form message caption
    private PictureData pictureData;

}
