package es.marugi.container.backend.adapter.out.persistence;

import es.marugi.container.backend.domain.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameJpaRepository extends JpaRepository<Game, Long> {
}

