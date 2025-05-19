package io.github.hrashk.kafka.starter;

import lombok.RequiredArgsConstructor;
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

import java.util.Properties;

@AutoConfiguration
@EnableConfigurationProperties(StarterProperties.class)
@RequiredArgsConstructor
public class StarterConfiguration {
    private final StarterProperties starterProperties;

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public KafkaConsumer<String, String> starterKafkaConsumer() {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, starterProperties.bootstrapServers());
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, starterProperties.consumer().groupId());
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, starterProperties.consumer().autoOffsetReset());
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, starterProperties.consumer().enableAutoCommit());
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return new KafkaConsumer<>(props);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean
    public KafkaProducer<String, String> starterKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, starterProperties.bootstrapServers());
        props.put(ProducerConfig.ACKS_CONFIG, starterProperties.producer().acks());
        props.put(ProducerConfig.RETRIES_CONFIG, starterProperties.producer().retries());
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, starterProperties.producer().retryBackoffMs());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, starterProperties.producer().deliveryTimeoutMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(props);
    }
}
