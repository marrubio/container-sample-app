package es.marugi.container.backend.adapter.out.persistence;

import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GamePersistenceAdapter implements GameRepository {

    private final GameJpaRepository gameRepository;

    @Autowired
    public GamePersistenceAdapter(GameJpaRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        gameRepository.deleteById(id);
    }
}

