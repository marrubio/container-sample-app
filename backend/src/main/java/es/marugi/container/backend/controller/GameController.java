package es.marugi.container.backend.controller;

import es.marugi.container.backend.dto.GameDTO;
import es.marugi.container.backend.entity.Game;
import es.marugi.container.backend.mapper.GameMapper;
import es.marugi.container.backend.service.GameCommandService;
import es.marugi.container.backend.service.GameQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameQueryService gameQueryService;
    @Autowired
    private GameCommandService gameCommandService;
    @Autowired
    private GameMapper gameMapper;

    @GetMapping
    public List<GameDTO> getGames() {
        return gameQueryService.getAllGames().stream()
            .map(gameMapper::toDTO)
            .collect(Collectors.toList());
    }

    @PostMapping
    public GameDTO createGame(@RequestBody GameDTO gameDTO) {
        Game game = gameMapper.toEntity(gameDTO);
        Game created = gameCommandService.createGame(game);
        return gameMapper.toDTO(created);
    }
}
