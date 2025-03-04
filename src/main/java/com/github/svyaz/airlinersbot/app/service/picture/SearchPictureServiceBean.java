package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.app.exception.PictureNotFoundException;
import com.github.svyaz.airlinersbot.datastore.service.UserStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchPictureServiceBean implements SearchPictureService {

    private final AirlinersClient airlinersClient;

    private final UserStorageService userStorageService;

    @Override
    public Picture search(User user, String keywords) {
        var picture = airlinersClient.search(keywords);

        user.setSearchResult(SearchResult.builder()
                .userId(user.getId())
                .keywords(keywords)
                .picture(picture)
                .build());

        userStorageService.save(user);

        return picture;
    }

    @Override
    public Picture next(User user) {
        var searchResult = Optional.ofNullable(user.getSearchResult())
                .orElseThrow(() -> new PictureNotFoundException("No search results for this user"));

        var nextPageUri = Optional.ofNullable(searchResult.getPicture())
                .map(Picture::getNextPageUri)
                .orElseThrow(() -> new PictureNotFoundException("No more search results"));

        var picture = airlinersClient.getPictureByUri(nextPageUri);

        user.getSearchResult().setPicture(picture);
        userStorageService.save(user);

        return picture;
    }

}
