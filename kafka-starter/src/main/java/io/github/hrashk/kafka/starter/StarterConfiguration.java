package io.github.hrashk.kafka.starter;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@AutoConfiguration
@EnableConfigurationProperties(StarterProperties.class)
@RequiredArgsConstructor
public class StarterConfiguration {
    private final StarterProperties starterProperties;

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public KafkaConsumer<String, GenericRecord> starterKafkaConsumer() {
        var props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, starterProperties.bootstrapServers(),
                AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, starterProperties.schemaRegistryUrl(),
                KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, starterProperties.consumer().specificAvroReader(),
                ConsumerConfig.GROUP_ID_CONFIG, starterProperties.consumer().groupId(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, starterProperties.consumer().autoOffsetReset(),
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, starterProperties.consumer().enableAutoCommit(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

        return new KafkaConsumer(props);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public KafkaProducer<String, GenericRecord> starterKafkaProducer() {
        var props = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, starterProperties.bootstrapServers(),
                AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, starterProperties.schemaRegistryUrl(),
                ProducerConfig.ACKS_CONFIG, starterProperties.producer().acks(),
                ProducerConfig.RETRIES_CONFIG, starterProperties.producer().retries(),
                ProducerConfig.RETRY_BACKOFF_MS_CONFIG, starterProperties.producer().retryBackoffMs(),
                ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, starterProperties.producer().deliveryTimeoutMs(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());

        return new KafkaProducer(props);
    }
}
