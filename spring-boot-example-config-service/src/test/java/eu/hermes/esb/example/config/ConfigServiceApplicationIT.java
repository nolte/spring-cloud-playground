package eu.hermes.esb.example.config;

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
@SpringBootTest(classes = ConfigServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"native", "test"})
public class ConfigServiceApplicationIT {
  @Autowired
  TestRestTemplate testRestTemplate;

  @Test
  public void loadSimpleConfigOverHttpWithProfile() {
    ResponseEntity<Map> config = testRestTemplate.getForEntity("/test-service/dev", Map.class);
    assertEquals(HttpStatus.OK, config.getStatusCode());
  }

  @Test
  public void loadSimpleConfigOverHttpWithProfile2() {
    ResponseEntity<Map> config = testRestTemplate.getForEntity("/test-service/dev/master", Map.class);
    assertEquals(HttpStatus.OK, config.getStatusCode());
  }

  @Test
  public void loadSimpleConfigOverHttpWithDefaultProfile() {
    ResponseEntity<Map> config = testRestTemplate.getForEntity("/test-service/default", Map.class);
    assertEquals(HttpStatus.OK, config.getStatusCode());
  }

  @Test
  public void loadSimpleConfigOverHttpNoProfile() {
    ResponseEntity<Map> config = testRestTemplate.getForEntity("/test-service", Map.class);
    assertEquals(HttpStatus.NOT_FOUND, config.getStatusCode());
  }

  @Test
  public void loadConfigsOfNotExistingService() {
    ResponseEntity<Map> config = testRestTemplate.getForEntity("/test-service-notexist", Map.class);
    assertEquals(HttpStatus.NOT_FOUND, config.getStatusCode());
  }

}
