package es.marugi.container.backend;

import es.marugi.container.backend.entity.Game;
import es.marugi.container.backend.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GameControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void createAndRetrieveGame() {
        // Crear un nuevo juego
        Game newGame = new Game();
        newGame.setTitle("Integration Test Game");
        newGame.setDescription("Game created by integration test");
        newGame.setScore(7.8);
        newGame.setDevelopmentYear(2026);

        // POST para crear el juego
        Game createdGame = webTestClient.post()
            .uri("/games")
            .bodyValue(newGame)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .returnResult().getResponseBody();

        assertThat(createdGame).isNotNull();
        assertThat(createdGame.getId()).isNotNull();
        assertThat(createdGame.getRecordedAt()).isNotNull();
        assertThat(createdGame.getTitle()).isEqualTo("Integration Test Game");

        // GET para recuperar todos los juegos
        Game[] games = webTestClient.get()
            .uri("/games")
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game[].class)
            .returnResult().getResponseBody();

        assertThat(games).isNotNull();
        boolean found = false;
        for (Game g : games) {
            if (g.getId().equals(createdGame.getId())) {
                found = true;
                assertThat(g.getTitle()).isEqualTo("Integration Test Game");
                assertThat(g.getRecordedAt()).isNotNull();
            }
        }
        assertThat(found).isTrue();
    }
}
