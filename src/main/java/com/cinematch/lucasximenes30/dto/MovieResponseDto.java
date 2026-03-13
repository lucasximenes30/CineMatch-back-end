package com.cinematch.lucasximenes30.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDto {

    private UUID id;
    private String title;
    private String genre;
    private LocalDate releaseYear;
    private Double ratingAverage;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

