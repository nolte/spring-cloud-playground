package eu.hermes.esb.example.hystrix;

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

import eu.hermes.esb.example.hystrix.HystrixDashboardApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HystrixDashboardApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class HystrixDashboardApplicationIT {
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void loadInfosFromAPI() {
		ResponseEntity<Map> config = testRestTemplate.getForEntity("/info", Map.class);
		assertEquals(HttpStatus.OK, config.getStatusCode());
	}

}
