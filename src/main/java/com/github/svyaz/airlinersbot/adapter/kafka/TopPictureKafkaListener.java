package com.github.svyaz.airlinersbot.adapter.kafka;

public interface TopPictureKafkaListener {
    void consume(String message);
}
