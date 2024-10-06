package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.mapper;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PictureIdGetterBean implements PictureIdGetter {

    @Override
    public Long getId(String raw) {
        var pattern = Pattern.compile("\\d+$");
        var matcher = pattern.matcher(raw);

        return matcher.find() ?
                Long.parseLong(matcher.group(0)) :
                null;
    }
}
