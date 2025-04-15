package com.github.svyaz.airlinersbot.lib.ratelimiter.service;

import com.github.svyaz.airlinersbot.lib.ratelimiter.conf.RateLimiterConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RateLimiterServiceBean implements RateLimiterService {

    private final RateLimiterConfig config;

    private ConcurrentHashMap<String, Queue<Long>> requestsQueue;

    @Override
    public long getDelay(String limiterName) {
        var callTime = System.currentTimeMillis();
        var timeWindow = config.getProperties().get(limiterName).getPeriodMillis();
        var callsNumber = config.getProperties().get(limiterName).getCallsNumber();
        var queue = requestsQueue.get(limiterName);

        while(!queue.isEmpty() && queue.peek() <= callTime - timeWindow) {
            queue.poll();
        }

        long delay = queue.size() < callsNumber ?
                0L :
                timeWindow - (callTime - Optional.ofNullable(queue.peek()).orElse(callTime));

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
