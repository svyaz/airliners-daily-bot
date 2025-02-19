package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.app.domain.User;

import java.util.Optional;

public interface SearchStorageService {

    SearchResult save(SearchResult searchResult);

    Optional<SearchResult> getSearchResult(User user);
}
