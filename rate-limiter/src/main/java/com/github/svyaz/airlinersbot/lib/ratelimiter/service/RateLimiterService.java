package com.github.svyaz.airlinersbot.lib.ratelimiter.service;

public interface RateLimiterService {

    long getDelay(String limiterName);
}
