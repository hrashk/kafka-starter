package io.github.hrashk.example.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "app.message-count=0")
class ExampleConsumerApplicationTest {
    @Value("${app.topic}")
    private String topic;

    @Test
    void sanityCheck() {
        assertNotNull(topic, "Topic should not be null");
        assertFalse(topic.trim().isEmpty(), "Topic should not be empty or only whitespace");
    }
}
