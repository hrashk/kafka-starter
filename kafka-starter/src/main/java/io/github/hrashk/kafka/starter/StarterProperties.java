package io.github.hrashk.kafka.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for the Kafka starter library.
 *
 * @param bootstrapServers Kafka bootstrap servers.
 */
@ConfigurationProperties(prefix = "kafka-starter")
public record StarterProperties(
        @DefaultValue("localhost:9092")
        String bootstrapServers,

        @DefaultValue
        ProducerProperties producer,

        @DefaultValue
        ConsumerProperties consumer
) {
    /**
     * @param autoOffsetReset  Offset reset policy: earliest, latest, none.
     * @param groupId          Consumer group ID.
     * @param enableAutoCommit Enable auto commit.
     */
    public record ConsumerProperties(
            @DefaultValue("earliest")
            String autoOffsetReset,

            @DefaultValue("example_group_id")
            String groupId,

            @DefaultValue("true")
            String enableAutoCommit
    ) {
    }

    /**
     * @param acks    Kafka producer acknowledgements: 0, 1, all.
     * @param retries Number of retries.
     * @param retryBackoffMs Retry backoff in milliseconds.
     * @param deliveryTimeoutMs Delivery timeout in milliseconds.
     */
    public record ProducerProperties(
            @DefaultValue("1")
            String acks,

            @DefaultValue("1000")
            String retries,

            @DefaultValue("100")
            String retryBackoffMs,

            @DefaultValue("120000")
            String deliveryTimeoutMs
    ) {
    }
}
