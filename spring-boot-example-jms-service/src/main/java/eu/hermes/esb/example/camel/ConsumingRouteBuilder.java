package eu.hermes.esb.example.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.MetricsConstants;

import eu.hermes.esb.example.model.ConsumingRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Consuming Camel Route Builder.
 * 
 * @author FinnernMal
 */
@Slf4j
@AllArgsConstructor
public class ConsumingRouteBuilder extends RouteBuilder {

  private ConsumingRouteModel consuming;

  @Override
  public void configure() throws Exception {
    log.debug("init consuming route {}", consuming.getId());
    from(consuming.getEntry()).routeId("jmsreceiver" + consuming.getId()).transacted()
        .log(LoggingLevel.INFO, "eu.hermes.esb.example.jms.receiver", "Receive Message from: " + consuming.getEntry())
        .setHeader(MetricsConstants.HEADER_METRIC_NAME, constant("consumed." + consuming.getId() + ".counter"))
        .to("metrics:counter:name.not.used");
  }

}
