package eu.hermes.esb.example.jms;

import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import eu.hermes.esb.example.SpringBootJMSExample;

/**
 * Test The application as integration Test. Use the config from
 * /src/test/resources/application-test.yml
 * 
 * @author FinnernMal
 */
@ActiveProfiles({"sender", "test"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootJMSExample.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExampleConfigIT {

  @EndpointInject(uri = "jms:queue:it.bridged.1.entry")
  Endpoint endpoint;

  @EndpointInject(uri = "mock:jms:queue:it.bridged.1.exit")
  protected MockEndpoint resultEndpoint;

  @Autowired
  ProducerTemplate producerTemplate;

  @Test
  public void checkBridgedRoute() throws Exception {
    System.out.println("dada");
    String body = "fooo";
    resultEndpoint.expectedBodiesReceived(body);
    producerTemplate.sendBody(endpoint, body);
    resultEndpoint.assertIsSatisfied();
  }
}
