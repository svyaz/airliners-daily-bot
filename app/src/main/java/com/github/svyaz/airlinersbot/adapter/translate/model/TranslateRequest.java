package com.github.svyaz.airlinersbot.adapter.translate.model;

import java.util.List;

public record TranslateRequest(
        List<String> texts,
        String targetLanguageCode,
        String format
) {
}
