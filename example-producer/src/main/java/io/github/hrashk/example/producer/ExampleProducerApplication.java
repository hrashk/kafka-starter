package io.github.hrashk.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleProducerApplication implements CommandLineRunner {
    private final KafkaProducer<String, String> producer;

    @Value("${app.topic}")
    private String topic;

    public ExampleProducerApplication(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        int i = 1;
        while (true) {
            producer.send(new ProducerRecord<>(topic, "key-" + i, "message-" + i));
            Thread.sleep(1000L);
            i++;
        }
    }
}
