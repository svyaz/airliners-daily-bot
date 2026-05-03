package com.github.svyaz.airlinersbot.adapter.htmlselector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.svyaz.airlinersbot.adapter.exeption.ParseException;
import com.github.svyaz.airlinersbot.adapter.htmlselector.model.PowChallengeData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class PowChallengeDataExtractorBean implements PowChallengeDataExtractor {

    private static final String POW_CHALLENGE_PREDICATE_TEXT = "window.POW_CHALLENGE_DATA=";
    private static final Predicate<String> POW_CHALLENGE_PREDICATE = html -> html.contains(POW_CHALLENGE_PREDICATE_TEXT);
    private static final String POW_CHALLENGE_DATA_PATTERN = "window\\.POW_CHALLENGE_DATA=\\{[^\\}]+\\}";

    private final ObjectMapper objectMapper;

    @Override
    public PowChallengeData apply(String rawHtml) {
        log.debug("apply <- {}", rawHtml);

        return Optional.ofNullable(rawHtml)
                .filter(POW_CHALLENGE_PREDICATE)
                .map(this::extractPowChallengeData)
                .orElse(null);
    }

    private PowChallengeData extractPowChallengeData(String html) {
        return Optional.of(POW_CHALLENGE_DATA_PATTERN)
                .map(Pattern::compile)
                .map(pattern -> pattern.matcher(html))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(0))
                .map(str -> str.replace(POW_CHALLENGE_PREDICATE_TEXT, ""))
                .map(this::convertJsToJson)
                .map(this::readPowChallengeData)
                .orElse(null);
    }

    private String convertJsToJson(String jsObject) {
        // Добавляем кавычки к ключам и заменяем одинарные кавычки на двойные
        return jsObject.replaceAll("([a-zA-Z_][a-zA-Z0-9_]*)\\s*:", "\"$1\":")
                .replace("'", "\"");
    }

    private PowChallengeData readPowChallengeData(String raw) {
        try {
            return objectMapper.readValue(raw, PowChallengeData.class);
        } catch (JsonProcessingException exception) {
            log.warn("read pow challenge data error", exception);
            throw new ParseException("Cannot read pow challenge data", exception);
        }
    }

}
