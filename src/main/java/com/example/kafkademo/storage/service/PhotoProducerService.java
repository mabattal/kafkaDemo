package com.example.kafkademo.storage.service;

import com.example.kafkademo.storage.configuration.properties.kafka.KafkaProperties;
import com.example.kafkademo.user.PhotoMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    public void sendPhotoUploadMessage(Long userId, String photoPath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new PhotoMessageDto(userId, photoPath));
            kafkaTemplate.send(kafkaProperties.getTopic().getPhotoUpload(), json);
        }
        catch (Exception e) {
            throw new RuntimeException("Kafka mesajı gönderilemedi", e);
        }

    }
}
