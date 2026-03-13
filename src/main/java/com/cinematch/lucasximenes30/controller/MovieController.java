package com.cinematch.lucasximenes30.controller;

import com.cinematch.lucasximenes30.dto.MovieCreateDto;
import com.cinematch.lucasximenes30.dto.MovieResponseDto;
import com.cinematch.lucasximenes30.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponseDto> createMovie(@Valid @RequestBody MovieCreateDto movieCreateDto) {
        MovieResponseDto movieResponseDto = movieService.createMovie(movieCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDto>> getAllMovies(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String title) {
        List<MovieResponseDto> movies;

        if (genre != null && !genre.isEmpty()) {
            movies = movieService.searchMoviesByGenre(genre);
        } else if (title != null && !title.isEmpty()) {
            movies = movieService.searchMoviesByTitle(title);
        } else {
            movies = movieService.getAllMovies();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieResponseDto> getMovieById(@PathVariable UUID movieId) {
        MovieResponseDto movieResponseDto = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movieResponseDto);
    }
}

