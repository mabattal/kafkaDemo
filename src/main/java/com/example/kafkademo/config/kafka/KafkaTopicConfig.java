package com.example.kafkademo.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic photoUploadDltTopic() {
        return TopicBuilder.name("photo-upload.DLT")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
