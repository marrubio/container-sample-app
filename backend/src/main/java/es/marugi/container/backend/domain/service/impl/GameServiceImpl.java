package es.marugi.container.backend.domain.service.impl;

import es.marugi.container.backend.domain.mapper.GameMapper;
import es.marugi.container.backend.domain.model.GameDTO;
import es.marugi.container.backend.adapter.in.rest.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.domain.model.Game;
import es.marugi.container.backend.adapter.out.persistence.GameRepository;
import es.marugi.container.backend.domain.service.GameCommandService;
import es.marugi.container.backend.domain.service.GameQueryService;
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
    public GameDTO createGame(GameDTO game) {
        Game entity = gameMapper.toEntity(game);
        entity.setRecordedAt(LocalDateTime.now());
        Game savedGame = gameRepository.save(entity);
        return gameMapper.toDTO(savedGame);
    }

    @Override
    public GameDTO updateGame(Long id, UpdateGameRequestDTO gameDTO) {
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
