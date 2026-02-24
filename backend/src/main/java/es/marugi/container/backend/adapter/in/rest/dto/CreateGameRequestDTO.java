package es.marugi.container.backend.adapter.in.rest.dto;

public record CreateGameRequestDTO(
    String title,
    String description,
    Integer developmentYear,
    Double score
) {}
