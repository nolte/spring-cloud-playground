package eu.hermes.esb.example.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.metrics.MetricsConstants;
import org.fluttercode.datafactory.impl.DataFactory;

import eu.hermes.esb.example.model.ProduceRouteModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Produce Camel Route Builder.
 * 
 * @author FinnernMal
 */
@Slf4j
@AllArgsConstructor
public class CronProduceRouteBuilder extends RouteBuilder {

  ProduceRouteModel producing;

  @Override
  public void configure() throws Exception {
    log.debug("init producing by Cron route {}", producing.getId());

    from("quartz2://timer" + producing.getId() + "?cron=" + producing.getCron())
        .routeId("produceMessagesByCron" + producing.getId())
        .setBody(constant((new DataFactory().getRandomText(producing.getProducedSize()))))
        .setHeader(MetricsConstants.HEADER_METRIC_NAME, constant("produce." + producing.getId() + ".counter"))
        .process(new Processor() {

          public void process(Exchange exchange) throws Exception {
            // add additional headers to the message from the configuration
            if (producing.getAdditionalHeaders() != null && !producing.getAdditionalHeaders().isEmpty()) {
              for (String key : producing.getAdditionalHeaders().keySet()) {
                exchange.getIn().setHeader(key, producing.getAdditionalHeaders().get(key));
              }
            }
          }
        }).setHeader("JMSDestination", constant(producing.getExit())).to("direct:produceJms");

  }

}
