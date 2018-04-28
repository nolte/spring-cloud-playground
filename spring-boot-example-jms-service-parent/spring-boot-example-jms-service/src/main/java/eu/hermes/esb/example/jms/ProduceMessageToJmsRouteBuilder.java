package eu.hermes.esb.example.jms;

import static eu.hermes.esb.example.SpringBootTechnicalProfiles.ROUTES_FROM_API;
import static eu.hermes.esb.example.SpringBootTechnicalProfiles.ROUTES_FROM_CONFIG;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Simple producing route Builder with incremet the Metrics
 *
 */
@Slf4j
@Component
@RefreshScope
@Profile({ ROUTES_FROM_API, ROUTES_FROM_CONFIG })
public class ProduceMessageToJmsRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:produceJms").routeId("jmssender")
				.log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.sender",
						"Send Message to: ${headers.CamelJmsDestination}")
				.toD("${headers.CamelJmsDestination}").to("metrics:counter:name.not.used");

	}

}
