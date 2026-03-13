package com.cinematch.lucasximenes30.controller;

import com.cinematch.lucasximenes30.dto.ReviewCreateDto;
import com.cinematch.lucasximenes30.dto.ReviewResponseDto;
import com.cinematch.lucasximenes30.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewCreateDto reviewCreateDto) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewResponseDto);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewResponseDto>> getUserReviews(@PathVariable UUID userId) {
        List<ReviewResponseDto> reviews = reviewService.getUserReviews(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<List<ReviewResponseDto>> getMovieReviews(@PathVariable UUID movieId) {
        List<ReviewResponseDto> reviews = reviewService.getMovieReviews(movieId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable UUID reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewResponseDto);
    }
}

