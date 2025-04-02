package com.github.svyaz.airlinersbot.lib.ratelimiter.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimiterProperties {
    /**
     * Number of allowed method calls per period
     */
    int callsNumber = 100;

    /**
     * Period for allowed method calls in milliseconds
     */
    int periodMillis = 1000;
}
