package es.marugi.container.backend.service;

import es.marugi.container.backend.entity.Game;

public interface GameCommandService {
    Game createGame(Game game);
}

