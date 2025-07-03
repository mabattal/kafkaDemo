package com.example.kafkademo.config.kafka;

import com.example.kafkademo.properties.kafka.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;


@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public NewTopic photoUploadDltTopic() {
        return TopicBuilder.name(kafkaProperties.getTopic().getPhotoUploadDlt())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
