package es.marugi.container.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.marugi.container.backend.adapter.out.persistence.GameRepository;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DemoApplicationTests {

	@Autowired
	private GameRepository gameRepository;

	@Test
	void contextLoads() {
	}

}
