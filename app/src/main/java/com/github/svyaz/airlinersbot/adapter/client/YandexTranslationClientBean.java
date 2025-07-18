package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.adapter.translate.model.TranslateRequest;
import com.github.svyaz.airlinersbot.adapter.translate.model.TranslateResponse;
import com.github.svyaz.airlinersbot.adapter.translate.model.TranslatedUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class YandexTranslationClientBean implements TranslationClient {

    private static final String format = "PLAIN_TEXT";

    private final WebClient webClient;

    public YandexTranslationClientBean(@Qualifier("translationWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<String> translate(List<String> texts, String lang) {
        log.debug("translate <- texts [{}], lang [{}]", texts, lang);

        return webClient.post()
                .uri("translate/v2/translate")
                .bodyValue(getRequest(texts, lang))
                .retrieve()
                .bodyToMono(TranslateResponse.class)
                .map(this::mapResponse)
                .block();
    }

    private TranslateRequest getRequest(List<String> texts, String targetLanguageCode) {
        return new TranslateRequest(texts, targetLanguageCode, format);
    }

    private List<String> mapResponse(TranslateResponse response) {
        return response.translations()
                .stream()
                .map(TranslatedUnit::text)
                .toList();
    }

}
