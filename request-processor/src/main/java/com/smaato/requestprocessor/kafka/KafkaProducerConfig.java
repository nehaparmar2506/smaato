package com.smaato.requestprocessor.kafka;

import com.smaato.requestprocessor.remote.data.EndPointCallStatus;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class KafkaProducerConfig {

    final KafkaProperties kafkaProperties;

  @Value("${kafka.topic.name}")
  private String topicName;

  @Value("${kafka.topic.no-of-partitions}")
  private int noOfPartitions;

  @Value("${kafka.topic.replication-factor}")
  private short replicationFactor;

    public KafkaProducerConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }


    @Bean
    public Map<String, Object> producerConfiguration() {
        Map<String, Object> properties = new HashMap<>(kafkaProperties.buildProducerProperties());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }

  @Bean
  ProducerFactory<Long, EndPointCallStatus> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfiguration());
    }

  @Bean
  KafkaTemplate<Long, EndPointCallStatus> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic topic() {
    return new NewTopic(topicName, noOfPartitions, replicationFactor);
    }
}
