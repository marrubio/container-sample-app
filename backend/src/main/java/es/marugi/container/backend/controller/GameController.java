package es.marugi.container.backend.controller;

import es.marugi.container.backend.entity.Game;
import es.marugi.container.backend.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public List<Game> getGames() {
        return gameRepository.findAll();
    }
}

