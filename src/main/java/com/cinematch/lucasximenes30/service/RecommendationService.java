package com.cinematch.lucasximenes30.service;

import com.cinematch.lucasximenes30.dto.RecommendationDto;
import com.cinematch.lucasximenes30.model.Movie;
import com.cinematch.lucasximenes30.model.Review;
import com.cinematch.lucasximenes30.model.User;
import com.cinematch.lucasximenes30.repository.MovieRepository;
import com.cinematch.lucasximenes30.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;
    private final UserService userService;

    public List<RecommendationDto> getRecommendations(UUID userId) {
        // Valida se o usuário existe
        User user = userService.getUserByIdOrThrow(userId);

        // Pega todas as reviews do usuário
        List<Review> userReviews = reviewRepository.findByUserId(userId);

        if (userReviews.isEmpty()) {
            return movieRepository.findAll()
                    .stream()
                    .limit(5)
                    .map(movie -> convertToRecommendationDto(movie, "Recomendação popular"))
                    .collect(Collectors.toList());
        }

        // Extrai gêneros e filmes já avaliados
        Set<String> userGenres = userReviews.stream()
                .map(review -> review.getMovie().getGenre())
                .collect(Collectors.toSet());

        Set<UUID> evaluatedMovies = userReviews.stream()
                .map(review -> review.getMovie().getId())
                .collect(Collectors.toSet());

        // Recomendações por gênero preferido
        List<RecommendationDto> recommendations = movieRepository.findAll()
                .stream()
                .filter(movie -> !evaluatedMovies.contains(movie.getId()))
                .filter(movie -> userGenres.stream()
                        .anyMatch(genre -> movie.getGenre().toLowerCase()
                                .contains(genre.toLowerCase())))
                .sorted((m1, m2) -> m2.getRatingAverage().compareTo(m1.getRatingAverage()))
                .limit(5)
                .map(movie -> convertToRecommendationDto(movie, "Baseado em seus gêneros favoritos"))
                .collect(Collectors.toList());

        // Se não tiver recomendações, retorna filmes com alta avaliação
        if (recommendations.isEmpty()) {
            recommendations = movieRepository.findAll()
                    .stream()
                    .filter(movie -> !evaluatedMovies.contains(movie.getId()))
                    .sorted((m1, m2) -> m2.getRatingAverage().compareTo(m1.getRatingAverage()))
                    .limit(5)
                    .map(movie -> convertToRecommendationDto(movie, "Filme com alta avaliação"))
                    .collect(Collectors.toList());
        }

        return recommendations;
    }

    public List<RecommendationDto> getRecommendationsByGenre(UUID userId, String genre) {
        userService.getUserByIdOrThrow(userId);

        List<Review> userReviews = reviewRepository.findByUserId(userId);
        Set<UUID> evaluatedMovies = userReviews.stream()
                .map(review -> review.getMovie().getId())
                .collect(Collectors.toSet());

        return movieRepository.findByGenreContainingIgnoreCase(genre)
                .stream()
                .filter(movie -> !evaluatedMovies.contains(movie.getId()))
                .sorted((m1, m2) -> m2.getRatingAverage().compareTo(m1.getRatingAverage()))
                .limit(5)
                .map(movie -> convertToRecommendationDto(movie, "Filme de " + genre + " com alta avaliação"))
                .collect(Collectors.toList());
    }

    private RecommendationDto convertToRecommendationDto(Movie movie, String reason) {
        return RecommendationDto.builder()
                .movieId(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .ratingAverage(movie.getRatingAverage())
                .description(movie.getDescription())
                .reason(reason)
                .build();
    }
}

