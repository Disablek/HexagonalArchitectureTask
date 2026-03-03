package com.onlinehotel.bookingservice.adapter.out.messaging.kafka.config;

import com.onlinehotel.bookingservice.adapter.out.messaging.event.DeleteBookingRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class KafkaConfig {

    KafkaCustomProperties kafkaProperties;

    @Bean
    public KafkaTemplate<String, DeleteBookingRequest> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    private DefaultKafkaProducerFactory<String, DeleteBookingRequest> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(),
                new StringSerializer(),
                new JacksonJsonSerializer<>());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonJsonSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAcks());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaProperties.getProducer().getDeliveryTimeout());
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaProperties.getProducer().getRetryBackoff());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getProducer().getLingerMs());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafkaProperties.getProducer().getEnableIdempotence());

        return props;
    }
}
