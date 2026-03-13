package com.cinematch.lucasximenes30.repository;

import com.cinematch.lucasximenes30.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByUserId(UUID userId);
    List<Review> findByMovieId(UUID movieId);
    Optional<Review> findByUserIdAndMovieId(UUID userId, UUID movieId);
}

