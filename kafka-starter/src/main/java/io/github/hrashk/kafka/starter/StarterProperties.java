package io.github.hrashk.kafka.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for the Kafka starter library.
 *
 * @param bootstrapServers Kafka bootstrap servers.
 * @param schemaRegistryUrl Schema registry URL.
 */
@ConfigurationProperties(prefix = "kafka-starter")
public record StarterProperties(
        @DefaultValue("localhost:9092")
        String bootstrapServers,

        @DefaultValue("localhost:8081")
        String schemaRegistryUrl,

        @DefaultValue
        ProducerProperties producer,

        @DefaultValue
        ConsumerProperties consumer
) {
    /**
     * @param autoOffsetReset  Offset reset policy: earliest, latest, none.
     * @param groupId          Consumer group ID.
     * @param enableAutoCommit Enable auto commit.
     * @param specificAvroReader Use specific Avro reader.
     */
    public record ConsumerProperties(
            @DefaultValue("earliest")
            String autoOffsetReset,

            @DefaultValue("example_group_id")
            String groupId,

            @DefaultValue("true")
            boolean enableAutoCommit,

            @DefaultValue("false")
            boolean specificAvroReader
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
            int retries,

            @DefaultValue("100")
            int retryBackoffMs,

            @DefaultValue("120000")
            int deliveryTimeoutMs
    ) {
    }
}
