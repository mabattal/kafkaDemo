package com.example.kafkademo.storage.controller;

import com.example.kafkademo.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StorageController {

    private final StorageService storageService;

    @PostMapping
    public ResponseEntity<String> store(@RequestParam("file") MultipartFile file) {
        String savedPath = storageService.saveFile(file);
        return ResponseEntity.ok(savedPath); // sadece test için dönüyoruz
    }
}