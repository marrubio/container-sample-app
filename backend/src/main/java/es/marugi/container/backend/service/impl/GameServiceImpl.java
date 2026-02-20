package es.marugi.container.backend.service.impl;

import es.marugi.container.backend.entity.Game;
import es.marugi.container.backend.repository.GameRepository;
import es.marugi.container.backend.service.GameCommandService;
import es.marugi.container.backend.service.GameQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameServiceImpl implements GameQueryService, GameCommandService {
    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game createGame(Game game) {
        game.setRecordedAt(LocalDateTime.now());
        return gameRepository.save(game);
    }
}

