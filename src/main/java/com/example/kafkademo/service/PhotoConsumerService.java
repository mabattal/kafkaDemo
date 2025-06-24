package com.example.kafkademo.service;

import com.example.kafkademo.model.PhotoMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoConsumerService {

    private final UserService userService;

    @KafkaListener(topics = "#{@kafkaProperties.topic.photoUpload}", groupId = "#{@kafkaProperties.groupId}")
    public void handlePhotoUpload(String message) {
        log.info("Kafka'dan gelen mesaj: {}", message);

        // JSON formatında path ve userId bilgisi bekleniyor: {"userId": 1, "path": "uploads/xyz.jpg"}
        try {
            ObjectMapper mapper = new ObjectMapper();
            PhotoMessageDto photoMessageDto = mapper.readValue(message, PhotoMessageDto.class);
            userService.updateProfileImage(photoMessageDto.getUserId(), photoMessageDto.getPhotoPath());
        } catch (Exception e) {
            log.error("Mesaj parse edilirken hata: {}", e.getMessage());
        }
    }
}