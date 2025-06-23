package com.example.kafkademo.storage.service;

import com.example.kafkademo.storage.configuration.properties.kafka.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void sendPhotoUploadMessage(String photoPath) {
        kafkaTemplate.send(kafkaProperties.getTopic().getPhotoUpload(), photoPath);
    }
}
