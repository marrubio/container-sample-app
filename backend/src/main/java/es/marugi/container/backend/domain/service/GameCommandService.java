package es.marugi.container.backend.domain.service;

import es.marugi.container.backend.domain.model.GameDTO;
import es.marugi.container.backend.adapter.in.rest.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.domain.model.Game;

public interface GameCommandService {
    GameDTO createGame(GameDTO game);
    GameDTO updateGame(Long id, UpdateGameRequestDTO gameDTO);
    void deleteGame(Long id);
}
