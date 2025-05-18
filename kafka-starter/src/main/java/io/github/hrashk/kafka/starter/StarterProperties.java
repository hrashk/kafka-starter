package io.github.hrashk.kafka.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

/**
 * Configuration properties for the Starter.
 * @param bootstrapServers Kafka bootstrap servers.
 * @param groupId Consumer group ID.
 */
@ConfigurationProperties(prefix = "kafka-starter")
public record StarterProperties(
        @DefaultValue("localhost:9092")
        String bootstrapServers,

        @DefaultValue("example_group_id")
        String groupId
) {
}
