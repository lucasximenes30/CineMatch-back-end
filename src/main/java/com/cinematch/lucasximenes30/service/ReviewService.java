package com.cinematch.lucasximenes30.service;

import com.cinematch.lucasximenes30.dto.ReviewCreateDto;
import com.cinematch.lucasximenes30.dto.ReviewResponseDto;
import com.cinematch.lucasximenes30.exception.BusinessException;
import com.cinematch.lucasximenes30.exception.ResourceNotFoundException;
import com.cinematch.lucasximenes30.model.Review;
import com.cinematch.lucasximenes30.model.Movie;
import com.cinematch.lucasximenes30.model.User;
import com.cinematch.lucasximenes30.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final MovieService movieService;

    public ReviewResponseDto createReview(ReviewCreateDto reviewCreateDto) {
        User user = userService.getUserByIdOrThrow(reviewCreateDto.getUserId());
        Movie movie = movieService.getMovieByIdOrThrow(reviewCreateDto.getMovieId());

        // Verifica se o usuário já avaliou este filme
        if (reviewRepository.findByUserIdAndMovieId(user.getId(), movie.getId()).isPresent()) {
            throw new BusinessException("Você já avaliou este filme");
        }

        Review review = Review.builder()
                .user(user)
                .movie(movie)
                .rating(reviewCreateDto.getRating())
                .build();

        Review savedReview = reviewRepository.save(review);
        movieService.updateMovieRating(movie.getId());
        return convertToResponseDto(savedReview);
    }

    public List<ReviewResponseDto> getUserReviews(UUID userId) {
        userService.getUserByIdOrThrow(userId);
        return reviewRepository.findByUserId(userId)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getMovieReviews(UUID movieId) {
        movieService.getMovieByIdOrThrow(movieId);
        return reviewRepository.findByMovieId(movieId)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public ReviewResponseDto getReviewById(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review não encontrada com ID: " + reviewId));
        return convertToResponseDto(review);
    }

    private ReviewResponseDto convertToResponseDto(Review review) {
        return ReviewResponseDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .movieId(review.getMovie().getId())
                .movieTitle(review.getMovie().getTitle())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

