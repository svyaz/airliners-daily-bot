package com.github.svyaz.airlinersbot.lib.ratelimiter.service;

import com.github.svyaz.airlinersbot.lib.ratelimiter.conf.RateLimiterConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceBean implements RateLimiterService {

    private final RateLimiterConfig config;

    private ConcurrentHashMap<String, Queue<Long>> requestsQueue;

    @Override
    public long getDelay(String limiterName) {
        long callTime = System.currentTimeMillis();
        long firstWaitingCallTime = callTime;

        var props = config.getProperties().get(limiterName);
        var queue = requestsQueue.get(limiterName);
        var iterator = queue.iterator();

        while (iterator.hasNext()) {
            long entry = iterator.next();
            if (callTime - entry >= props.getPeriodMillis()) {
                //протух запрос
                iterator.remove();
            } else {
                // если попали сюда, значит удалили все протухшие запросы
                firstWaitingCallTime = entry;
                break;
            }
        }

        long delay = (queue.size() < props.getCallsNumber()) ?
                0L :
                props.getPeriodMillis() - (callTime - firstWaitingCallTime);

        queue.add(callTime);

        return delay;
    }

    @PostConstruct
    private void init() {
        requestsQueue = new ConcurrentHashMap<>();

        config.getProperties()
                .keySet()
                .forEach(k -> requestsQueue.put(k, new LinkedList<>()));
    }
}
