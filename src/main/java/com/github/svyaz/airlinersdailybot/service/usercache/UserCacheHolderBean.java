package com.github.svyaz.airlinersdailybot.service.usercache;

import com.github.svyaz.airlinersdailybot.model.UserCache;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserCacheHolderBean implements UserCacheHolder, InitializingBean {

    private ConcurrentHashMap<String, UserCache> usersCache;

    @Override
    public String getUserNextSearchResultUri(Long userId, Long chatId) {
        return usersCache.getOrDefault(getKey(userId, chatId), new UserCache())
                .getNextSearchResultUri();
    }

    @Override
    public void setUserNextSearchResultUri(Long userId, Long chatId, String uri) {
        var key = getKey(userId, chatId);
        var cache = usersCache.getOrDefault(key, new UserCache());
        cache.setNextSearchResultUri(uri);
        usersCache.put(key, cache);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        usersCache = new ConcurrentHashMap<>();
    }

    private String getKey(Long userId, Long chatId) {
        return String.format("%d-%d", userId, chatId);
    }

}
