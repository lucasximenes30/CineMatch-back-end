package com.cinematch.lucasximenes30.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewCreateDto {

    @NotNull(message = "ID do usuário é obrigatório")
    private UUID userId;

    @NotNull(message = "ID do filme é obrigatório")
    private UUID movieId;

    @NotNull(message = "Avaliação é obrigatória")
    @Min(value = 1, message = "Avaliação deve ser no mínimo 1")
    @Max(value = 5, message = "Avaliação deve ser no máximo 5")
    private Integer rating;
}

