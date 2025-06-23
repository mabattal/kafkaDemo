package com.example.kafkademo.upload.service;

import com.example.kafkademo.storage.configuration.properties.kafka.KafkaProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoConsumerService {

    private final KafkaProperties kafkaProperties;

    @KafkaListener(topics = "#{@kafkaProperties.topic.photoUpload}", groupId = "#{@kafkaProperties.groupId}")
    public void handlePhotoUpload(String photoPath) {
        // Kaydetme işlemi burada yapılır
        System.out.println("Fotoğraf geldi: " + photoPath);
    }
}