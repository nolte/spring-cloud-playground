package eu.hermes.esb.example.web.controller;

import static eu.hermes.esb.example.SpringBootTechnicalProfiles.ROUTES_FROM_API;

import java.util.List;

import javax.validation.Valid;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ConsumingRouteModel;
import eu.hermes.esb.example.model.ProduceRouteModel;
import eu.hermes.esb.example.model.RouteModel;
import eu.hermes.esb.example.services.RouteControllingService;
import eu.hermes.esb.example.web.model.BridgingRouteDTO;
import eu.hermes.esb.example.web.model.ConsumingRouteDTO;
import eu.hermes.esb.example.web.model.ProducingRouteDTO;
import eu.hermes.esb.example.web.model.RouteDTO;
import eu.hermes.esb.example.web.model.RouteNotExistException;
import eu.hermes.esb.example.web.model.RouteState;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/routes")
@Profile({ ROUTES_FROM_API })
public class RouteControllerImpl implements RouteController {

	@Autowired
	private RouteControllingService routeControllingService;

	@Autowired
	private Mapper mapper;

	@ApiOperation(value = "addRoute", nickname = "addRoute")
	@RequestMapping(method = RequestMethod.POST)
	public String addRoute(@Valid @RequestBody RouteDTO route) throws Exception {
		RouteModel mapped = null;
		if (route instanceof BridgingRouteDTO)
			mapped = mapper.map(route, BridgeRouteModel.class);
		else if (route instanceof ProducingRouteDTO)
			mapped = mapper.map(route, ProduceRouteModel.class);
		else if (route instanceof ConsumingRouteDTO)
			mapped = mapper.map(route, ConsumingRouteModel.class);

		return routeControllingService.addRoute(mapped);
	}

	@ApiOperation(value = "removeRoute", nickname = "removeRoute")
	@RequestMapping(value = "/{routeId}", method = RequestMethod.DELETE)
	public boolean removeRoute(@PathVariable("routeId") String routeId) throws Exception {
		routeControllingService.removeRouteById(routeId);
		return true;
	}

	@ApiOperation(value = "getRoute", nickname = "getRoute")
	@RequestMapping(value = "/{routeId}/status", method = RequestMethod.GET)
	public String getRoute(@PathVariable("routeId") String routeId) throws RouteNotExistException {
		String routeState = routeControllingService.routeState(routeId);
		return routeState;
	}

	@ApiOperation(value = "setRouteState", nickname = "setRouteState")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Route state not changed allways the given state"),
			@ApiResponse(code = 202, message = "Route state changed") })
	@RequestMapping(value = "/{routeId}/status", method = RequestMethod.POST)
	public ResponseEntity<String> setRouteState(
			@ApiParam(value = "route to change state", required = true) @PathVariable("routeId") String routeId,
			@ApiParam(value = "new state for route", defaultValue = "toggle", required = true) @RequestBody(required = false) RouteState state)
			throws RouteNotExistException {

		if (state == null)
			state = RouteState.TOGGLE;

		ResponseEntity response = null;

		String routeState = routeControllingService.routeState(routeId);

		if (state.equals(RouteState.START) && routeState.equals("Started")
				|| state.equals(RouteState.STOP) && routeState.equals("Stopped")) {
			response = new ResponseEntity<String>(routeState, HttpStatus.OK);
		} else {

			routeControllingService.setRouteState(routeId, state, routeState);
			routeState = routeControllingService.routeState(routeId);
			response = new ResponseEntity<String>(routeState, HttpStatus.CREATED);
		}
		return response;

	}

	@ApiOperation(value = "getRoutes", nickname = "getRoutes")
	@RequestMapping(method = RequestMethod.GET)
	@Override
	public List<String> allExistingRoutes() {
		log.debug("load all relevant routes from context");
		return routeControllingService.loadAllRelevantRoutes();
	}

}
