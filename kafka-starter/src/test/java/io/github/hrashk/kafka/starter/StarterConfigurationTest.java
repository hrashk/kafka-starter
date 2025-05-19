package io.github.hrashk.kafka.starter;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = StarterConfiguration.class,
        properties = {"kafka-starter.consumer.group-id=test-group"})
@ContextConfiguration(initializers = KafkaInitializer.class)
class StarterConfigurationTest {
    static final String TOPIC = "test-topic";

    @Autowired
    KafkaConsumer<String, String> consumer;

    @Autowired
    KafkaProducer<String, String> producer;

    /**
     * Note that consumer.poll() is a blocking operation that waits for the specified duration to receive messages
     * which is enough to wait for a single message.
     * It makes it unnecessary to run the consumer asynchronously in a separate thread and wait for messages to arrive.
     */
    @Test
    void producingAndConsumingSingleMessage() {
        assertEquals("test-group", consumer.groupMetadata().groupId());

        producer.send(new ProducerRecord<>(TOPIC, "test-key", "test-message"));

        consumer.subscribe(List.of(TOPIC));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(15L));
        assertEquals(1, records.count());

        records.forEach(
                r -> assertEquals("test-key", r.key()));
    }
}
