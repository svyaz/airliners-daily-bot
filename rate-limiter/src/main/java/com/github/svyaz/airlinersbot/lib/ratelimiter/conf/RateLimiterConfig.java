package com.github.svyaz.airlinersbot.lib.ratelimiter.conf;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "ratelimiter")
public class RateLimiterConfig {

    private Map<String, LimiterProperties> properties;
}
