package com.github.svyaz.airlinersbot.app.service.translate;

import com.github.svyaz.airlinersbot.adapter.client.TranslationClient;
import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.Translatable;
import com.github.svyaz.airlinersbot.conf.CacheConfig;
import com.github.svyaz.airlinersbot.conf.TranslationConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PictureTranslateServiceBean implements PictureTranslateService {

    private final TranslationConfig translationConfig;

    private final TranslationClient client;

    @Cacheable(value = CacheConfig.PICTURE_LANG_CACHE, key = "#picture.id + '_' + #langCode")
    @Override
    public Picture translate(Picture picture, String langCode) {
        log.debug("translate <- picture [{}], langCode [{}]", picture, langCode);

        return Optional.of(langCode)
                .filter(Predicate.not(code -> translationConfig.getDefaultLangCode().equals(code)))
                .filter(code -> translationConfig.getTranslations().contains(code))
                .map(code -> translatePicture(picture, langCode))
                .orElse(picture);
    }

    @SneakyThrows
    private Picture translatePicture(Picture picture, String langCode) {
        List<String> texts = getTextsForTranslation(picture);

        return Optional.of(texts)
                .filter(Predicate.not(CollectionUtils::isEmpty))
                .map(t -> client.translate(t, langCode))
                .map(t -> getTranslated(picture, t))
                .orElse(picture);
    }

    @SneakyThrows
    private List<String> getTextsForTranslation(Picture picture) {
        List<String> texts = new ArrayList<>();

        for (Field field : picture.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Translatable.class)) {
                field.setAccessible(true);
                var origValue =  Optional.ofNullable(field.get(picture))
                        .map(o -> (String) o)
                        .orElse("");
                texts.add(origValue);
            }
        }

        return texts;
    }

    @SneakyThrows
    private Picture getTranslated(Picture picture, List<String> translatedTexts) {
        log.debug("getTranslated: translatedTexts [{}]", translatedTexts);

        var newPicture = picture.clone();
        int textIdx = 0;

        for (Field field : newPicture.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Translatable.class)) {
                field.setAccessible(true);
                field.set(newPicture, translatedTexts.get(textIdx++));
            }
        }

        return newPicture;
    }

}
