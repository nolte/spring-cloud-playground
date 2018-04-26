package eu.hermes.esb.example.dashboard;

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
@SpringBootTest(classes = DashboardServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class DashboardServiceApplicationIT {
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void loadSimpleConfigOverHttpWithProfile() {
		ResponseEntity<Map> config = testRestTemplate.getForEntity("/info", Map.class);
		assertEquals(HttpStatus.OK, config.getStatusCode());
	}

}
