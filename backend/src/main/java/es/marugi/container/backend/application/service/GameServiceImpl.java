package es.marugi.container.backend.application.service;

import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.application.mapper.GameMapper;
import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.application.dto.UpdateGameDTO;
import es.marugi.container.backend.domain.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameQueryService, GameCommandService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    public List<GameDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(gameMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDTO createGame(CreateGameDTO game) {
        Game entity = gameMapper.toEntity(game);
        entity.setRecordedAt(LocalDateTime.now());
        Game savedGame = gameRepository.save(entity);
        return gameMapper.toDTO(savedGame);
    }

    @Override
    public GameDTO updateGame(Long id, UpdateGameDTO gameDTO) {
        Game existing = gameRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Game not found"));
        existing.setTitle(gameDTO.title());
        existing.setDescription(gameDTO.description());
        existing.setDevelopmentYear(gameDTO.developmentYear());
        existing.setScore(gameDTO.score());
        Game updatedGame = gameRepository.save(existing);
        return gameMapper.toDTO(updatedGame);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

}

