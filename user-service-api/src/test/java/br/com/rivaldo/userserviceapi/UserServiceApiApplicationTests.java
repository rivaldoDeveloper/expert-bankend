package br.com.rivaldo.userserviceapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = "CONFIG_SERVER_URI=http://localhost")
class UserServiceApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
