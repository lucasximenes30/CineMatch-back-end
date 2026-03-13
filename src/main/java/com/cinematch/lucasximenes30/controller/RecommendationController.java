package com.cinematch.lucasximenes30.controller;

import com.cinematch.lucasximenes30.dto.RecommendationDto;
import com.cinematch.lucasximenes30.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecommendationDto>> getRecommendations(
            @PathVariable UUID userId,
            @RequestParam(required = false) String genre) {
        List<RecommendationDto> recommendations;

        if (genre != null && !genre.isEmpty()) {
            recommendations = recommendationService.getRecommendationsByGenre(userId, genre);
        } else {
            recommendations = recommendationService.getRecommendations(userId);
        }

        return ResponseEntity.ok(recommendations);
    }
}

