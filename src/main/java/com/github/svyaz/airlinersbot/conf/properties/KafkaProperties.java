package com.github.svyaz.airlinersbot.conf.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private String bootstrapServers;
    private String topPicturesTopicName;
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private String groupId;
    private String autoOffsetReset;

}
