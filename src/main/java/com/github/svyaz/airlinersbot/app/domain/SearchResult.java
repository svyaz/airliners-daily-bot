package com.github.svyaz.airlinersbot.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult {

    private Long userId;
    private String keywords;
    private Picture picture;
}
