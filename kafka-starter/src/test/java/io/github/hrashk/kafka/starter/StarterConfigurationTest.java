package io.github.hrashk.kafka.starter;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = StarterConfiguration.class,
        properties = {"kafka-starter.consumer.group-id=test-group",
                "kafka-starter.schema-registry-url=mock://test-scope-name",
                "kafka-starter.consumer.specific-avro-reader=true"})
@ContextConfiguration(initializers = KafkaInitializer.class)
class StarterConfigurationTest {
    static final String TOPIC = "test-topic";

    @Autowired
    KafkaConsumer<String, GenericRecord> consumer;

    @Autowired
    KafkaProducer<String, GenericRecord> producer;

    /**
     * Note that consumer.poll() is a blocking operation that waits for the specified duration to receive messages
     * which is enough to wait for a single message.
     * It makes it unnecessary to run the consumer asynchronously in a separate thread and wait for messages to arrive.
     */
    @Test
    void producingAndConsumingSingleMessage() {
        assertEquals("test-group", consumer.groupMetadata().groupId());

        String email = "test@example.com";
        var msg = new Message("Jane Dow", email, 18);

        String key = "test-key";
        producer.send(new ProducerRecord<>(TOPIC, key, msg));

        consumer.subscribe(List.of(TOPIC));
        ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofSeconds(15L));
        assertEquals(1, records.count());

        for (ConsumerRecord<String, GenericRecord> cr : records) {
            assertEquals(key, cr.key());

            assertInstanceOf(Message.class, cr.value());

            if (cr.value() instanceof Message m) {
                assertEquals(email, String.valueOf(m.getEmail()));
            }
        }
    }
}
