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

import java.lang.reflect.Field;
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
                .map(code -> translateFields(picture, langCode))
                .orElse(picture);
    }

    @SneakyThrows
    private Picture translateFields(Picture picture, String langCode) {
        var newPicture = picture.clone();

        for (Field field : newPicture.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Translatable.class)) {
                log.debug("translateFields: field [{}], langCode [{}]", field.getName(), langCode);

                field.setAccessible(true);
                var origValue = (String) field.get(newPicture);

                var newValue = Optional.ofNullable(origValue)
                        .map(v -> client.translate(v, langCode))
                        .orElse(origValue);

                field.set(newPicture, newValue);
            }
        }

        return newPicture;
    }

}
