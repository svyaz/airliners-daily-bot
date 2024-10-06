package com.github.svyaz.airlinersdailybot.AirlinersDailyBot.service;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class PictureUpdateServiceTest {

    @Test
    void parseLong() {
        System.out.println(
                Pattern.compile("\\d+$")
                .matcher("/photo/Russia-Air-Force/Antonov-An-124-100-Ruslan/7665435")
                        .find()
        );

        assert true;
    }
}
