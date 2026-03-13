package com.cinematch.lucasximenes30.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationDto {

    private UUID movieId;
    private String title;
    private String genre;
    private Double ratingAverage;
    private String description;
    private String reason;
}

