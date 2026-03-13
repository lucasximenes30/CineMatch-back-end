package com.cinematch.lucasximenes30.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieCreateDto {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 1, max = 255, message = "Título deve ter entre 1 e 255 caracteres")
    private String title;

    @NotBlank(message = "Gênero é obrigatório")
    @Size(min = 2, max = 50, message = "Gênero deve ter entre 2 e 50 caracteres")
    private String genre;

    private LocalDate releaseYear;

    private String description;
}

