package com.github.svyaz.airlinersbot.datastore.model;

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
@Table(name = "search_result")
public class SearchResultEntity {

    @Id
    private Long userId;
    private String keywords;
    private String pictureUri;
    private String nextPictureUri;
    private LocalDateTime updateTime;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
