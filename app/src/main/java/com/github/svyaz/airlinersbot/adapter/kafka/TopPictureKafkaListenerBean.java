package com.github.svyaz.airlinersbot.adapter.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.svyaz.airlinersbot.app.domain.subscription.PictureMessage;
import com.github.svyaz.airlinersbot.app.service.subscription.TopPictureUserSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TopPictureKafkaListenerBean implements TopPictureKafkaListener {

    private final ObjectMapper objectMapper;

    private final TopPictureUserSenderService sender;

    @KafkaListener(
            topics = "${spring.kafka.top-pictures-topic-name}",
            groupId = "${spring.kafka.group-id}"
    )
    public void consume(String message) {
        log.info("consume <-");

        Optional.of(message)
                .map(this::readMessage)
                .ifPresent(sender);
    }

    @SneakyThrows
    private PictureMessage readMessage(String message) {
        return objectMapper.readValue(message, PictureMessage.class);
    }

}
