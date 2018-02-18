package eu.hermes.esb.example.jms;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RefreshScope
@Profile({"sender"})
public class ProduceMessageToJmsRouteBuilder extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:produceJms").routeId("jmssender")
        .log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.sender", "Send Message to: ${header.JMSDestination}")
        .toD("${header.JMSDestination}").to("metrics:counter:name.not.used");

  }

}
