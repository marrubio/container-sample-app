package es.marugi.container.backend.application.service;

import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.application.mapper.GameMapper;
import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.adapter.out.persistence.GameJpaRepository;
import es.marugi.container.backend.application.dto.UpdateGameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameQueryService, GameCommandService {
    private final GameJpaRepository gameRepositoryPort;
    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameJpaRepository gameRepositoryPort, GameMapper gameMapper) {
        this.gameRepositoryPort = gameRepositoryPort;
        this.gameMapper = gameMapper;
    }

    @Override
    public List<GameDTO> getAllGames() {
        return gameRepositoryPort.findAll().stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO createGame(CreateGameDTO game) {
        Game entity = gameMapper.toEntity(game);
        entity.setRecordedAt(LocalDateTime.now());
        Game savedGame = gameRepositoryPort.save(entity);
        return gameMapper.toDTO(savedGame);
    }

    @Override
    public GameDTO updateGame(Long id, UpdateGameDTO gameDTO) {
        Game existing = gameRepositoryPort.findById(id)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        existing.setTitle(gameDTO.title());
        existing.setDescription(gameDTO.description());
        existing.setDevelopmentYear(gameDTO.developmentYear());
        existing.setScore(gameDTO.score());
        Game updatedGame = gameRepositoryPort.save(existing);
        return gameMapper.toDTO(updatedGame);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepositoryPort.deleteById(id);
    }

}

