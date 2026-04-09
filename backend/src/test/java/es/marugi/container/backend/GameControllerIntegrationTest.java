package es.marugi.container.backend;

import es.marugi.container.backend.domain.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
            .uri("/api/games")
            .bodyValue(newGame)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().valueMatches("Location", ".*/api/games/\\d+$")
            .expectBody(Game.class)
            .returnResult().getResponseBody();

        assertThat(createdGame).isNotNull();
        assertThat(createdGame.getId()).isNotNull();
        assertThat(createdGame.getRecordedAt()).isNotNull();
        assertThat(createdGame.getTitle()).isEqualTo("Integration Test Game");

        Game retrievedGame = webTestClient.get()
            .uri("/api/games/" + createdGame.getId())
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .returnResult().getResponseBody();

        assertThat(retrievedGame).isNotNull();
        assertThat(retrievedGame.getId()).isEqualTo(createdGame.getId());
        assertThat(retrievedGame.getTitle()).isEqualTo("Integration Test Game");

        // GET para recuperar todos los juegos
        Game[] games = webTestClient.get()
            .uri("/api/games")
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

    @Test
    void updateGame() {
        // Crear un nuevo juego
        Game newGame = new Game();
        newGame.setTitle("Update Test Game");
        newGame.setDescription("Game to be updated");
        newGame.setScore(5.0);
        newGame.setDevelopmentYear(2020);

        // POST para crear el juego
        Game createdGame = webTestClient.post()
            .uri("/api/games")
            .bodyValue(newGame)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Game.class)
            .returnResult().getResponseBody();

        assertThat(createdGame).isNotNull();
        Long id = createdGame.getId();

        // Crear DTO de actualización
        var updateRequest = new java.util.HashMap<String, Object>();
        updateRequest.put("title", "Updated Game Title");
        updateRequest.put("description", "Updated description");
        updateRequest.put("developmentYear", 2022);
        updateRequest.put("score", 9);

        // PUT para actualizar el juego
        Game updatedGame = webTestClient.put()
            .uri("/api/games/" + id)
            .bodyValue(updateRequest)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Game.class)
            .returnResult().getResponseBody();

        assertThat(updatedGame).isNotNull();
        assertThat(updatedGame.getTitle()).isEqualTo("Updated Game Title");
        assertThat(updatedGame.getDescription()).isEqualTo("Updated description");
        assertThat(updatedGame.getDevelopmentYear()).isEqualTo(2022);
        assertThat(updatedGame.getScore()).isEqualTo(9);
       // assertThat(updatedGame.getRecordedAt()).isEqualTo(createdGame.getRecordedAt()); // La fecha no debe cambiar
    }

    @Test
    void createGameReturnsBadRequestWhenPayloadIsInvalid() {
        var invalidRequest = new java.util.HashMap<String, Object>();
        invalidRequest.put("title", "");
        invalidRequest.put("description", "Valid description");
        invalidRequest.put("developmentYear", 2026);
        invalidRequest.put("score", 11);

        webTestClient.post()
            .uri("/api/games")
            .bodyValue(invalidRequest)
            .exchange()
            .expectStatus().isBadRequest()
            .expectBody()
            .jsonPath("$.status").isEqualTo(400)
            .jsonPath("$.error").isEqualTo("Bad Request")
            .jsonPath("$.message").exists()
            .jsonPath("$.path").isEqualTo("/api/games");
    }

    @Test
    void updateGameReturnsNotFoundWhenGameDoesNotExist() {
        var updateRequest = new java.util.HashMap<String, Object>();
        updateRequest.put("title", "Missing Game");
        updateRequest.put("description", "This game does not exist");
        updateRequest.put("developmentYear", 2022);
        updateRequest.put("score", 9.5);

        webTestClient.put()
            .uri("/api/games/999999")
            .bodyValue(updateRequest)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.error").isEqualTo("Not Found")
            .jsonPath("$.message").isEqualTo("Game with id 999999 not found")
            .jsonPath("$.path").isEqualTo("/api/games/999999");
    }

    @Test
    void deleteGameReturnsNotFoundWhenGameDoesNotExist() {
        webTestClient.delete()
            .uri("/api/games/999999")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody()
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.error").isEqualTo("Not Found")
            .jsonPath("$.message").isEqualTo("Game with id 999999 not found")
            .jsonPath("$.path").isEqualTo("/api/games/999999");
    }
}
