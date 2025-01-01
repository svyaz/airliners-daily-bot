package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;

public interface SearchStorageService {

    SearchResult save(SearchResult searchResult);
}
