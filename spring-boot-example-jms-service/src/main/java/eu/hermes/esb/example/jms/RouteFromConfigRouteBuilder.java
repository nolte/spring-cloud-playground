package eu.hermes.esb.example.jms;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import eu.hermes.esb.example.camel.BridgeRouteBuilder;
import eu.hermes.esb.example.camel.ConsumingRouteBuilder;
import eu.hermes.esb.example.camel.CronProduceRouteBuilder;
import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ConsumingRouteModel;
import eu.hermes.esb.example.model.ExampleAppProperties;
import eu.hermes.esb.example.model.ProduceRouteModel;
import lombok.extern.slf4j.Slf4j;

/**
 * Base Configuration for init all Expected Routes from the config file.
 * 
 * @author FinnernMal
 */
@Slf4j
@Configuration
public class RouteFromConfigRouteBuilder {

  @Autowired
  ExampleAppProperties configs;

  @Autowired
  CamelContext camelContext;

  @PostConstruct
  public void configure() throws Exception {
    log.debug("init routes from config");

    if (configs.getBridge() != null) {
      for (BridgeRouteModel bridge : configs.getBridge()) {
        camelContext.addRoutes(new BridgeRouteBuilder(bridge));

      }
    }
    if (configs.getConsuming() != null) {
      for (ConsumingRouteModel consuming : configs.getConsuming()) {
        camelContext.addRoutes(new ConsumingRouteBuilder(consuming));
      }
    }
    if (configs.getProducing() != null) {
      for (ProduceRouteModel producing : configs.getProducing()) {
        camelContext.addRoutes(new CronProduceRouteBuilder(producing));
      }
    }
  }

}
