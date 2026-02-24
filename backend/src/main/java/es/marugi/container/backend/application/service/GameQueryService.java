package es.marugi.container.backend.application.service;


import es.marugi.container.backend.application.dto.GameDTO;

import java.util.List;

public interface GameQueryService {
    List<GameDTO> getAllGames();
}

