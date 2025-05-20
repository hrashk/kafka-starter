package io.github.hrashk.example.consumer;

import io.github.hrashk.example.UserProfile;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.List;

@SpringBootApplication
public class ExampleConsumerApplication implements CommandLineRunner {
    private final KafkaConsumer<String, GenericRecord> consumer;

    @Value("${app.topic}")
    private String topic;

    public ExampleConsumerApplication(KafkaConsumer<String, GenericRecord> consumer) {
        this.consumer = consumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        consumer.subscribe(List.of(topic));

        while (true) {
            ConsumerRecords<String, GenericRecord> records = consumer.poll(Duration.ofSeconds(1));
            records.forEach(record -> {
                var user = (UserProfile) record.value();

                System.out.printf("Received Message topic = %s, partition = %d, offset = %d, key = %s, value = %s%n",
                        record.topic(), record.partition(), record.offset(), record.key(), user);
            });

            // alternatively, call commitSync() if performance is not an issue
            consumer.commitAsync();
        }
    }
}
