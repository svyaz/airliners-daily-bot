package com.github.svyaz.airlinersbot.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.conf.properties.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopPictureKafkaSenderBean implements TopPictureKafkaSender {

    private final KafkaProperties kafkaConfig;

    private final KafkaTemplate<String, String> kafkaSender;

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public void send(PictureMessage message) {
        log.info("send <- {}", message);

        kafkaSender.send(
                kafkaConfig.getTopPicturesTopicName(),
                objectMapper.writeValueAsString(message)
        );
    }
}
