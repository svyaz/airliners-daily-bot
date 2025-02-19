package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.exception.PictureNotFoundException;
import com.github.svyaz.airlinersbot.datastore.service.SearchStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchPictureServiceBean implements SearchPictureService {

    private final AirlinersClient airlinersClient;

    private final SearchStorageService searchStorageService;

    @Override
    public Picture search(User user, String keywords) {
        var picture = airlinersClient.search(keywords);

        var searchResult = SearchResult.builder()
                .userId(user.getId())
                .keywords(keywords)
                .picture(picture)
                .build();

        searchStorageService.save(searchResult);

        return picture;
    }

    @Override
    public Picture next(User user) {
        var searchResult = searchStorageService.getSearchResult(user)
                .orElseThrow(() -> new PictureNotFoundException("No search results for this user"));

        var nextPageUri = Optional.ofNullable(searchResult.getPicture())
                .map(Picture::getNextPageUri)
                .orElseThrow(() -> new PictureNotFoundException("No more search results"));

        var picture = airlinersClient.getPictureByUri(nextPageUri);

        searchResult.setPicture(picture);
        searchStorageService.save(searchResult);

        return picture;
    }

}
