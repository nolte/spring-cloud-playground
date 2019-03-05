package eu.hermes.esb.example.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.MetricsConstants;

import eu.hermes.esb.example.model.BridgeRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Camel Route Builder for bridging messages from Uri to Uri
 * 
 * @author FinnernMal
 */
@Slf4j
@AllArgsConstructor
public class BridgeRouteBuilder extends RouteBuilder {

  private BridgeRouteModel bridge;

  @Override
  public void configure() throws Exception {
    log.debug("init bridge route {}", bridge.getId());

    from(bridge.getEntry()).routeId("bridge" + bridge.getId())
        .log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.bridge",
            "forward message from:" + bridge.getEntry() + " to:" + bridge.getExit())
        .setHeader(MetricsConstants.HEADER_METRIC_NAME, constant("bridged." + bridge.getId() + ".counter"))
        .setHeader("JMSDestination", constant(bridge.getExit())).to("direct:produceJms").end();

  }

}
