package com.example.kafkademo.storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final PhotoProducerService photoProducerService;

    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Yüklenen dosya boş");
            }

            String folderPath = "uploads";
            File uploadDir = new File(folderPath);
            if (!uploadDir.exists()) {
                boolean created = uploadDir.mkdirs();
                if (!created) {
                    throw new RuntimeException("Uploads klasörü oluşturulamadı");
                }
            }

            String originalFilename = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_");
            String filePath = folderPath + File.separator + UUID.randomUUID() + "_" + originalFilename;

            Path destinationPath = Paths.get(filePath);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            photoProducerService.sendPhotoUploadMessage(filePath);

            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Dosya kaydedilemedi: " + e.getMessage(), e);
        }
    }
}