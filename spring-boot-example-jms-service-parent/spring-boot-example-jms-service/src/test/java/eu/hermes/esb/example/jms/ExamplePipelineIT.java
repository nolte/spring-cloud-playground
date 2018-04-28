package eu.hermes.esb.example.jms;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import eu.hermes.esb.example.SpringBootJMSExample;
import eu.hermes.esb.example.web.model.BridgingRouteDTO;
import eu.hermes.esb.example.web.model.ConsumingRouteDTO;
import eu.hermes.esb.example.web.model.MessageDTO;
import eu.hermes.esb.example.web.model.ProducingRouteDTO;
import eu.hermes.esb.example.web.model.RouteState;

@ActiveProfiles({ "test", "senderweb" })
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootJMSExample.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExamplePipelineIT {

	@Autowired
	TestRestTemplate testRestTemplate;

	@EndpointInject(uri = "mock:jms:queue:it.bridged.1.exit")
	protected MockEndpoint resultEndpoint;

	@Test
	public void createSomeRoutePipeline() throws InterruptedException {
		resultEndpoint.expectedMessageCount(1);

		testRestTemplate.postForEntity("/api/routes",
				ConsumingRouteDTO.builder().uri("jms:queue:it.bridged.1.exit").id("consuming").build(), String.class);

		testRestTemplate.postForEntity("/api/routes", BridgingRouteDTO.builder().uri("jms:queue:it.bridged.1.entry")
				.bridgeTarget("jms:queue:it.bridged.1.exit").id("bridge").build(), String.class);

		testRestTemplate.postForEntity("/api/routes", ProducingRouteDTO.builder().uri("jms:queue:it.bridged.1.entry")
				.id("produce").cron("0/10+*+*+*+*+?").build(), String.class);

		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void produceMessageOverRestInterface() throws InterruptedException {
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.message(0).header("test").isEqualTo("myValue");
		resultEndpoint.message(0).body().isEqualTo("test");

		ResponseEntity<Boolean> config = testRestTemplate.postForEntity("/api/message", MessageDTO.builder()
				.destinationUri("mock:jms:queue:it.bridged.1.exit").payload("test").header("test", "myValue").build(),
				Boolean.class);

		assertEquals(HttpStatus.OK, config.getStatusCode());
		resultEndpoint.assertIsSatisfied();
	}

	@Test
	public void loadStateFromNonExistingRoute() throws InterruptedException {
		ResponseEntity<String> config = testRestTemplate.getForEntity("/api/routes/non-exising/status", String.class);
		assertEquals(HttpStatus.NOT_FOUND, config.getStatusCode());
	}

	@Test
	public void loadStateFromExistingRoute() throws InterruptedException {
		ResponseEntity<String> config = testRestTemplate.getForEntity("/api/routes/jmssender/status", String.class);
		assertEquals(HttpStatus.OK, config.getStatusCode());
		assertEquals("Started", config.getBody());
	}

	@Test
	public void setRouteStateNonChangedState() throws InterruptedException {
		ResponseEntity<String> config = testRestTemplate.postForEntity("/api/routes/jmssender/status", RouteState.START,
				String.class);
		assertEquals(HttpStatus.OK, config.getStatusCode());
		assertEquals("Started", config.getBody());
	}

	@Test
	public void setRouteStateChangedState() throws InterruptedException {
		ResponseEntity<String> config = testRestTemplate.postForEntity("/api/routes/jmssender/status", RouteState.STOP,
				String.class);
		assertEquals(HttpStatus.CREATED, config.getStatusCode());
		assertEquals("Stopped", config.getBody());
	}

	@Test
	public void setRouteStateChangedStateToggle() throws InterruptedException {
		ResponseEntity<String> config = testRestTemplate.postForEntity("/api/routes/jmssender/status",
				RouteState.TOGGLE, String.class);
		assertEquals(HttpStatus.CREATED, config.getStatusCode());
		assertEquals("Stopped", config.getBody());
		config = testRestTemplate.postForEntity("/api/routes/jmssender/status", RouteState.TOGGLE, String.class);
		assertEquals(HttpStatus.CREATED, config.getStatusCode());
		assertEquals("Started", config.getBody());
	}
}
