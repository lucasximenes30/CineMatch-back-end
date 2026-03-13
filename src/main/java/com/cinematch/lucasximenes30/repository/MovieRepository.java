package com.cinematch.lucasximenes30.repository;

import com.cinematch.lucasximenes30.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieRepository extends JpaRepository<Movie, UUID> {
    List<Movie> findByGenreContainingIgnoreCase(String genre);
    List<Movie> findByTitleContainingIgnoreCase(String title);
}

