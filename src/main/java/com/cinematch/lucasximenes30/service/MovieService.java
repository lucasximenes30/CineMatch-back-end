package com.cinematch.lucasximenes30.service;

import com.cinematch.lucasximenes30.dto.MovieCreateDto;
import com.cinematch.lucasximenes30.dto.MovieResponseDto;
import com.cinematch.lucasximenes30.exception.ResourceNotFoundException;
import com.cinematch.lucasximenes30.model.Movie;
import com.cinematch.lucasximenes30.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieResponseDto createMovie(MovieCreateDto movieCreateDto) {
        Movie movie = Movie.builder()
                .title(movieCreateDto.getTitle())
                .genre(movieCreateDto.getGenre())
                .releaseYear(movieCreateDto.getReleaseYear())
                .description(movieCreateDto.getDescription())
                .build();

        Movie savedMovie = movieRepository.save(movie);
        return convertToResponseDto(savedMovie);
    }

    public MovieResponseDto getMovieById(UUID movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com ID: " + movieId));
        return convertToResponseDto(movie);
    }

    public List<MovieResponseDto> getAllMovies() {
        return movieRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<MovieResponseDto> searchMoviesByGenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public List<MovieResponseDto> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public Movie getMovieByIdOrThrow(UUID movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com ID: " + movieId));
    }

    public void updateMovieRating(UUID movieId) {
        Movie movie = getMovieByIdOrThrow(movieId);
        // Aqui você pode implementar a lógica de atualização de média de avaliação
        movieRepository.save(movie);
    }

    private MovieResponseDto convertToResponseDto(Movie movie) {
        return MovieResponseDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .releaseYear(movie.getReleaseYear())
                .ratingAverage(movie.getRatingAverage())
                .description(movie.getDescription())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }
}

