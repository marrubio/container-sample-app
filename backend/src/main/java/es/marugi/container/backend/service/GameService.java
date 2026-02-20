package es.marugi.container.backend.service;

import es.marugi.container.backend.entity.Game;
import java.util.List;

public interface GameService {
    List<Game> getAllGames();
    Game createGame(Game game);
}

