package es.marugi.container.backend.application.service;

import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.application.dto.UpdateGameDTO;

public interface GameCommandService {
    GameDTO createGame(CreateGameDTO game);
    GameDTO updateGame(Long id, UpdateGameDTO updateGameDTO);
    void deleteGame(Long id);
}

