package es.marugi.container.backend.dto;

import java.time.LocalDateTime;

public record CreateGameRequestDTO(
    String title,
    String description,
    Integer developmentYear,
    Double score
) {}
