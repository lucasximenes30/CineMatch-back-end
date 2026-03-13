package com.cinematch.lucasximenes30.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDto {

    private UUID id;
    private UUID userId;
    private String userName;
    private UUID movieId;
    private String movieTitle;
    private Integer rating;
    private LocalDateTime createdAt;
}

