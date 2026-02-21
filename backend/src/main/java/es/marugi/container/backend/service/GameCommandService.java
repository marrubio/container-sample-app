package es.marugi.container.backend.service;

import es.marugi.container.backend.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.entity.Game;

public interface GameCommandService {
    Game createGame(Game game);
    Game updateGame(Long id, UpdateGameRequestDTO gameDTO);
    void deleteGame(Long id);
}
