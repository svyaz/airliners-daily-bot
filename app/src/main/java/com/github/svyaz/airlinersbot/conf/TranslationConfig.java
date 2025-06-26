package com.github.svyaz.airlinersbot.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class TranslationConfig {

    private String defaultLangCode;

    private List<String> translations;
}
