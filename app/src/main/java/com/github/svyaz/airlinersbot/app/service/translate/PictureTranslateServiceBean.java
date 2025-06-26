package com.github.svyaz.airlinersbot.app.service.translate;

import com.github.svyaz.airlinersbot.app.domain.Picture;
import com.github.svyaz.airlinersbot.app.domain.Translatable;
import com.github.svyaz.airlinersbot.conf.TranslationConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class PictureTranslateServiceBean implements PictureTranslateService {

    private final TranslationConfig translationConfig;

    @Override
    //todo : тут кэширование
    public Picture translate(Picture picture, String langCode) {
        return Optional.of(langCode)
                .filter(Predicate.not(code -> translationConfig.getDefaultLangCode().equals(code)))
                .filter(code -> translationConfig.getTranslations().contains(code))
                .map(code -> translateFields(picture, langCode))
                .orElse(picture);
    }

    @SneakyThrows
    private Picture translateFields(Picture picture, String langCode) {
        for (Field field : picture.getClass().getDeclaredFields()){
            if (field.isAnnotationPresent(Translatable.class)) {
                field.setAccessible(true);
                String origValue = (String) field.get(picture);
                String translatedValue = tr(origValue, langCode);
                field.set(picture, translatedValue);
            }
        }

        return picture;
    }

    //todo : вместо этого - вызов сервиса в адаптере
    private String tr(String fieldValue, String langCode) {
        return fieldValue;
    }

}
