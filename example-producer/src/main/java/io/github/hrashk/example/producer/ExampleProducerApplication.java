package io.github.hrashk.example.producer;

import io.github.hrashk.example.UserProfile;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleProducerApplication implements CommandLineRunner {
    private final KafkaProducer<String, GenericRecord> producer;

    @Value("${app.topic}")
    private String topic;

    @Value("${app.message-count}")
    private Integer messageCount;

    public ExampleProducerApplication(KafkaProducer<String, GenericRecord> producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int i = 1;

        while (i <= messageCount) {
            var msg = new UserProfile("Janet Doey", "email" + i + "@example.com", 18);
            ProducerRecord<String, GenericRecord> record = new ProducerRecord<>(topic, "key-" + i, msg);
            producer.send(record);
            System.out.printf("Sent Message: %s%n", record);
            Thread.sleep(1000L);
            i++;
        }
    }
}
