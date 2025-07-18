package com.github.svyaz.airlinersbot.adapter.client;

import java.util.List;

public interface TranslationClient {

    List<String> translate(List<String> texts, String lang);
}
