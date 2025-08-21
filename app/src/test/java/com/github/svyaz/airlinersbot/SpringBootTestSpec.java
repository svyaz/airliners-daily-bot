package com.github.svyaz.airlinersbot;

import com.github.svyaz.airlinersbot.adapter.bot.AirlinersBot;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(properties = {
        "spring.config.location=file:config/application-dev.yaml",
        "spring.profiles.active=dev",
        "bot.token=XYZ"
})
@Testcontainers
public abstract class SpringBootTestSpec {

    @MockitoBean
    AirlinersBot airlinersBot;

    @Container
    public static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("apache/kafka:3.8.1"))
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.producer.acks", () -> "all");
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.consumer.group-id", () -> "airliners-bot-app-group");
    }

}
