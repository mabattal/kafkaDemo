package com.example.kafkademo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoMessageDto {
    private Long userId;
    private String photoPath;
}