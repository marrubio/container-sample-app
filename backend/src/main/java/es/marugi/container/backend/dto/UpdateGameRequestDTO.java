package es.marugi.container.backend.dto;

public record UpdateGameRequestDTO(
    String title,
    String description,
    Integer developmentYear,
    Double score
    // Añade otros campos editables si es necesario
) {}
