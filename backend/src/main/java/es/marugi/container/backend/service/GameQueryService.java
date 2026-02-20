package es.marugi.container.backend.service;

import es.marugi.container.backend.entity.Game;
import java.util.List;

public interface GameQueryService {
    List<Game> getAllGames();
}

