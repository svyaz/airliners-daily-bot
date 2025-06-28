package com.github.svyaz.airlinersbot.adapter.translate.model;

public record TranslateRequest(
        String q,
        String source,
        String target,
        String format
) {
}
