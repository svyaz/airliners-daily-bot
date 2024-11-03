package com.github.svyaz.airlinersdailybot.service.usercache;

public interface UserCacheHolder {

    String getUserNextSearchResultUri(Long userId, Long chatId);

    void setUserNextSearchResultUri(Long userId, Long chatId, String uri);
}
