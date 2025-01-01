package com.github.svyaz.airlinersbot.app.service.picture;

import com.github.svyaz.airlinersbot.adapter.client.AirlinersClient;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.User;
import com.github.svyaz.airlinersbot.datastore.service.SearchStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchPictureServiceBean implements SearchPictureService {

    private final AirlinersClient airlinersClient;

    private final SearchStorageService searchStorageService;

    @Override
    public Picture search(User user, String keywords) {
        var picture = airlinersClient.search(keywords);

        //todo mapper: picture -> searchResult
        var searchResult = SearchResult.builder()
                .userId(user.getId())
                .keywords(keywords)
                .picture(picture)
                .build();

        var savedPicture = searchStorageService.save(searchResult);

        return picture;
    }
}
