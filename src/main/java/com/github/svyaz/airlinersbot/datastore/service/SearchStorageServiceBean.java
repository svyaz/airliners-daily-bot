package com.github.svyaz.airlinersbot.datastore.service;

import com.github.svyaz.airlinersbot.app.domain.SearchResult;
import com.github.svyaz.airlinersbot.datastore.mapper.SearchResultMapper;
import com.github.svyaz.airlinersbot.datastore.repository.SearchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchStorageServiceBean implements SearchStorageService {

    private final SearchResultRepository repository;

    private final SearchResultMapper mapper;

    @Override
    public SearchResult save(SearchResult searchResult) {
        return Optional.of(searchResult)
                .map(mapper::toSearchResultEntity)
                .map(repository::save)
                .map(mapper::toSearchResult)
                .orElse(null);
    }
}
