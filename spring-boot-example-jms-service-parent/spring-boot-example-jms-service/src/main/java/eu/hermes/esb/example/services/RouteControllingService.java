package eu.hermes.esb.example.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.hermes.esb.example.camel.AbstractRouteBuilder;
import eu.hermes.esb.example.camel.BridgeRouteBuilder;
import eu.hermes.esb.example.camel.ConsumingRouteBuilder;
import eu.hermes.esb.example.camel.CronProduceRouteBuilder;
import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ConsumingRouteModel;
import eu.hermes.esb.example.model.ProduceRouteModel;
import eu.hermes.esb.example.model.RouteModel;
import eu.hermes.esb.example.web.model.RouteNotExistException;
import eu.hermes.esb.example.web.model.RouteState;

@Service
public class RouteControllingService {

	@Autowired
	CamelContext camelContext;

	@Autowired
	ProducerTemplate producerTemplate;

	public String addRoute(RouteModel model) throws Exception {
		AbstractRouteBuilder routeBuilder = null;
		if (model instanceof ConsumingRouteModel)
			routeBuilder = new ConsumingRouteBuilder((ConsumingRouteModel) model);
		else if (model instanceof BridgeRouteModel)
			routeBuilder = new BridgeRouteBuilder((BridgeRouteModel) model);
		else if (model instanceof ProduceRouteModel)
			routeBuilder = new CronProduceRouteBuilder((ProduceRouteModel) model);

		camelContext.addRoutes(routeBuilder);
		return routeBuilder.getCamelRouteId();
	}

	public List<String> loadAllRelevantRoutes() {
		List<Route> routes = camelContext.getRoutes();
		List<String> relevantRoutes = new ArrayList<String>();
		for (Route route : routes) {
			String id = route.getId();
			if (isControllingRoute(id)) {
				relevantRoutes.add(id);
			}
		}
		return relevantRoutes;
	}

	private boolean isControllingRoute(String id) {
		return isConsumingRoute(id) || isProducingRoute(id) || isBridgedRoute(id);
	}

	private boolean isBridgedRoute(String id) {
		return id.startsWith(BridgeRouteBuilder.PREFIX);
	}

	private boolean isProducingRoute(String id) {
		return id.startsWith(CronProduceRouteBuilder.PREFIX);
	}

	private boolean isConsumingRoute(String id) {
		return id.startsWith(ConsumingRouteBuilder.PREFIX);
	}

	public void removeRouteById(String routeId) throws Exception {
		camelContext.removeRoute(routeId);
	}

	public String routeState(String routeId) {
		String status = producerTemplate.requestBody(routeStatusURI(routeId), null, String.class);
		if (status == null)
			throw new RouteNotExistException(routeId);

		return status;
	}

	private String routeStatusURI(String routeId) {
		return "controlbus:route?routeId=" + routeId + "&action=status";
	}

	public void setRouteState(String routeId, RouteState state, String currenState) {

		String status = "stop";
		if (state.equals(RouteState.TOGGLE)) {
			if (currenState.equals("Started")) {
				status = "stop";
			} else if (currenState.equals("Stopped")) {
				status = "start";
			} else if (currenState.equals("Suspended")) {
				status = "resume";
			}
		}else {
			status = state.name().toLowerCase();
		}


		producerTemplate.requestBody("controlbus:route?routeId=" + routeId + "&action=" + status, null, String.class);

	}

}
