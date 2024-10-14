package com.github.svyaz.airlinersdailybot.conf;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Getter
@Configuration
public class MessagesConfig {

    @Value("${app.defaultLangCode}")
    private String defaultLangCode;

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setCacheSeconds(3600);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.of(defaultLangCode));
        return messageSource;
    }
}
