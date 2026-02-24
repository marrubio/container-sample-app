package es.marugi.container.backend.domain.service;


import es.marugi.container.backend.domain.model.GameDTO;

import java.util.List;

public interface GameQueryService {
    List<GameDTO> getAllGames();
}
