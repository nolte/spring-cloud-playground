package eu.hermes.esb.example.web.controller;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eu.hermes.esb.example.web.model.RouteDTO;
import feign.Headers;
import feign.RequestLine;

@FeignClient("${example-fergin-app-name:example-jms-service}")
public interface RouteController {

	@RequestLine("GET " + "/api/routes")
	@RequestMapping(method = RequestMethod.GET, value = "/api/routes")
	List<String> allExistingRoutes();

	@RequestLine("POST " + "/api/routes")
	@Headers("Content-Type: application/json")
	public String addRoute(RouteDTO route) throws Exception;

}
