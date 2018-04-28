package eu.hermes.esb.example.jms;

import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles({ "test", "senderconfig" })
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExampleConfigIT {

	@EndpointInject(uri = "mock:jms:queue:it.bridged.1.exit")
	protected MockEndpoint resultEndpoint;

	@Test
	public void checkConfigFromFile() throws Exception {
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.message(0).header("foo").isEqualTo("faa");
		resultEndpoint.assertIsSatisfied();
	}
}
