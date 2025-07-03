package com.example.kafkademo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DltConsumerService {

    @KafkaListener(
            topics = "#{@kafkaProperties.topic.photoUploadDlt}",
            groupId = "#{@kafkaProperties.consumer.dltGroupId}")
    public void handleDltMessage(ConsumerRecord<String, String> record) {
        log.error("- DLT MESAJI ALINDI -");
        log.error("Topic: {}", record.topic());
        log.error("Partition: {}", record.partition());
        log.error("Offset: {}", record.offset());
        log.error("Key: {}", record.key());
        log.error("Value: {}", record.value());
        log.error("Headers: {}", record.headers());

        // Gerekirse bu mesajlar DB'ye, bir tabloya veya log dosyasına yazılabilir.
        // Alternatif: Uyarı sistemi / mail / Slack bildirimi
    }
}