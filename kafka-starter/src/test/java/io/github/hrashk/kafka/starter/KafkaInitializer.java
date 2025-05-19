package io.github.hrashk.kafka.starter;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class KafkaInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final KafkaContainer KAFKA_CONTAINER =
            new KafkaContainer(DockerImageName.parse("apache/kafka-native:latest"))
                    .waitingFor(Wait.forListeningPort());

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        KAFKA_CONTAINER.start();
        TestPropertyValues.of(
                "kafka-starter.bootstrap-servers=" + KAFKA_CONTAINER.getBootstrapServers()
        ).applyTo(applicationContext.getEnvironment());
    }
}
