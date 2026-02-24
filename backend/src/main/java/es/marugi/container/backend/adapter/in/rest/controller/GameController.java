package es.marugi.container.backend.adapter.in.rest.controller;

import es.marugi.container.backend.adapter.in.rest.dto.GameResponseDTO;
import es.marugi.container.backend.adapter.in.rest.dto.CreateGameRequestDTO;
import es.marugi.container.backend.adapter.in.rest.dto.UpdateGameRequestDTO;
import es.marugi.container.backend.adapter.in.rest.mapper.GameRestMapper;
import es.marugi.container.backend.application.dto.CreateGameDTO;
import es.marugi.container.backend.application.dto.GameDTO;
import es.marugi.container.backend.application.service.GameCommandService;
import es.marugi.container.backend.application.service.GameQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.ResponseEntity;
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
    private GameRestMapper gameMapper;

    @GetMapping
    public List<GameResponseDTO> getGames() {
        return gameQueryService.getAllGames().stream()
            .map(gameMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @PostMapping
    public GameResponseDTO createGame(@RequestBody CreateGameRequestDTO createRequest) {
        CreateGameDTO createGameDTO = gameMapper.toDto(createRequest);
        GameDTO created = gameCommandService.createGame(createGameDTO);
        return gameMapper.toResponseDTO(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> updateGame(@PathVariable Long id, @RequestBody UpdateGameRequestDTO updateRequest) {
        GameDTO updated = gameCommandService.updateGame(id,gameMapper.toDto(updateRequest));
        return ResponseEntity.ok(gameMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameCommandService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

}
