package com.cinematch.lucasximenes30.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "titulo")
    @NotNull
    private String title;

    @Column(name = "genero")
    @NotNull
    private String genre;

    @Column(name = "anoDeLancamento")
    private LocalDate releaseYear;

    @Column(name = "mediaDeAvaliacao")
    private double ratingAverage;

}
