package com.example.kafkademo.service;

import com.example.kafkademo.entity.User;
import com.example.kafkademo.repository.UserRepository;
import com.example.kafkademo.util.MultipartInputStreamFileResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public boolean updateProfileImage(Long userId, String photoPath) {
        return userRepository.findById(userId).map(user -> {

            // idempotent kontrol: aynı path zaten kayıtlıysa işlem yapma
            if (photoPath.equals(user.getProfileImage())) {
                log.info("Profil fotoğrafı zaten aynı. Güncellenmedi.");
                return false;
            }

            user.setProfileImage(photoPath);
            userRepository.save(user);
            log.info("User {} profil fotoğrafı {} olarak güncellendi", userId, photoPath);
            return true;
        }).orElseGet(() -> {
            log.warn("User {} bulunamadı, güncelleme yapılmadı", userId);
            return false;
        });
    }

    public String uploadProfileImage(MultipartFile file, Long userId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Yüklenen dosya boş olamaz.");
        }

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("Geçersiz kullanıcı ID.");
        }

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        body.add("userId", userId.toString());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8081/store",
                requestEntity,
                String.class
        );

        return response.getBody();
    }
}
