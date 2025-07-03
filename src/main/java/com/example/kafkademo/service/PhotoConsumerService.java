package com.example.kafkademo.service;

import com.example.kafkademo.model.PhotoMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PhotoConsumerService {

    private final UserService userService;

    @KafkaListener(
            topics = "#{@kafkaProperties.topic.photoUpload}",
            groupId = "#{@kafkaProperties.consumer.groupId}")
    public void handlePhotoUpload(String message, Acknowledgment ack) {
        log.info("Kafka'dan gelen mesaj: {}", message);

        // JSON formatında path ve userId bilgisi bekleniyor: {"userId": 1, "path": "uploads/xyz.jpg"}
        try {
            ObjectMapper mapper = new ObjectMapper();
            PhotoMessageDto photoMessageDto = mapper.readValue(message, PhotoMessageDto.class);

            boolean updated = userService.updateProfileImage(photoMessageDto.getUserId(), photoMessageDto.getPhotoPath());
            if (updated) {
                log.info("Profil resmi güncellendi. Offset commit ediliyor.");
                //log.info("Profil resmi güncellendi. ack.acknowledge() çağırılmadı!.");
                ack.acknowledge();
            } else {
                log.warn("Profil güncellenemedi, offset commit edilmedi.");
            }

        } catch (Exception e) {
            log.error("Mesaj işlenirken hata: {} | Mesaj: {}", e.getMessage(), message);
            throw new RuntimeException("JSON parse hatası", e); // 🔥 Dışarı fırlat
        }
    }
}