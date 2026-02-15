package es.marugi.container.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.marugi.container.backend.entity.Game;
import es.marugi.container.backend.repository.GameRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private GameRepository gameRepository;

	@Test
	void contextLoads() {
		// Verifica que hay al menos un registro en la tabla Game
		assertThat(gameRepository.count()).isGreaterThan(0);
	}

	@Test
	void dataSqlScriptExecuted() {
		// Verifica que existe el registro insertado por data.sql
		Game game = gameRepository.findAll().stream()
			.filter(g -> "Test Game".equals(g.getTitle()) &&
				"Juego de prueba para tests automáticos".equals(g.getDescription()) &&
				Double.valueOf(9.5).equals(g.getScore()) &&
				Integer.valueOf(2024).equals(g.getDevelopmentYear()))
			.findFirst()
			.orElse(null);
		assertThat(game).isNotNull();
	}

}
