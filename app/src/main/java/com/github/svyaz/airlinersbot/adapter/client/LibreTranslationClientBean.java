package com.github.svyaz.airlinersbot.adapter.client;

import com.github.svyaz.airlinersbot.adapter.translate.model.TranslateRequest;
import com.github.svyaz.airlinersbot.adapter.translate.model.TranslateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
//@Service
public class LibreTranslationClientBean /*implements TranslationClient*/ {

    private static final String sourceLangCode = "en";
    private static final String textFormal = "text";

    private final WebClient webClient;

    public LibreTranslationClientBean(@Qualifier("translationWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    /*@Override
    public String translate(String text, String lang) {
        log.debug("translate <- text [{}], lang [{}]", text, lang);

        return webClient.post()
                .uri("translate")
                .bodyValue(getRequest(text, lang))
                .retrieve()
                .bodyToMono(TranslateResponse.class)
                .map(TranslateResponse::translatedText)
                .block();
    }

    private TranslateRequest getRequest(String text, String targetLangCode) {
        return new TranslateRequest(text, sourceLangCode, targetLangCode, textFormal);
    }*/

}
