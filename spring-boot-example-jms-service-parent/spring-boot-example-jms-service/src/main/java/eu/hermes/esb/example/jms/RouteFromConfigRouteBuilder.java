package eu.hermes.esb.example.jms;

import static eu.hermes.esb.example.SpringBootTechnicalProfiles.ROUTES_FROM_CONFIG;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ConsumingRouteModel;
import eu.hermes.esb.example.model.ExampleAppProperties;
import eu.hermes.esb.example.model.ProduceRouteModel;
import eu.hermes.esb.example.services.RouteControllingService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@Profile({ ROUTES_FROM_CONFIG })
public class RouteFromConfigRouteBuilder {

	@Autowired
	ExampleAppProperties configs;

	@Autowired
	RouteControllingService routeControllingService;

	@PostConstruct
	public void configure() throws Exception {
		log.debug("init routes from config");

		if (configs.getBridge() != null) {
			for (BridgeRouteModel bridge : configs.getBridge()) {
				routeControllingService.addRoute(bridge);

			}
		}
		if (configs.getConsuming() != null) {
			for (ConsumingRouteModel consuming : configs.getConsuming()) {
				routeControllingService.addRoute(consuming);
			}
		}
		if (configs.getProducing() != null) {
			for (ProduceRouteModel producing : configs.getProducing()) {
				routeControllingService.addRoute(producing);

			}
		}
	}

}
