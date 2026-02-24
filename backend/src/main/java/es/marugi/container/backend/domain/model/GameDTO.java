package es.marugi.container.backend.domain.model;

import java.time.LocalDateTime;

public record GameDTO(
    Long id,
    String title,
    String description,
    Integer developmentYear,
    Double score,
    LocalDateTime recordedAt
) {}
