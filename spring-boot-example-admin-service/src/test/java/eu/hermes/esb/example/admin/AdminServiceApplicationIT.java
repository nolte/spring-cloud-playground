package eu.hermes.esb.example.admin;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AdminServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class AdminServiceApplicationIT {
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void loadInfosFromAPI() {
		ResponseEntity<Map> config = testRestTemplate.getForEntity("/actuator/info", Map.class);
		assertEquals(HttpStatus.OK, config.getStatusCode());
	}

}
