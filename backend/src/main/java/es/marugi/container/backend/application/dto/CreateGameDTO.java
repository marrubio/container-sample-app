package es.marugi.container.backend.application.dto;

import java.time.LocalDateTime;

public record CreateGameDTO(
    String title,
    String description,
    Integer developmentYear,
    Double score
) {}

