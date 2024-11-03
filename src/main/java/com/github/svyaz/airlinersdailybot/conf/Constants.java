package com.github.svyaz.airlinersdailybot.conf;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String PARSE_MODE = "HTML";
    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";

    public static final String SHOW_TOP_CB_DATA = "SHOW_TOP";
    public static final String SHOW_NEXT_CB_DATA = "SHOW_NEXT";
}
