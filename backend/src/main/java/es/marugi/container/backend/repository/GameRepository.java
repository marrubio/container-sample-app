package es.marugi.container.backend.repository;

import es.marugi.container.backend.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}

