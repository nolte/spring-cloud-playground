package eu.hermes.esb.example.frontend.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import eu.hermes.esb.example.frontend.model.ServiceModel;
import eu.hermes.esb.example.web.controller.ProduceMessageController;
import eu.hermes.esb.example.web.controller.RouteController;
import eu.hermes.esb.example.web.model.MessageDTO;
import eu.hermes.esb.example.web.model.RouteDTO;
import feign.Feign;
import feign.Feign.Builder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

@Service
public class WorkerLocateService {

	@Autowired
	DiscoveryClient discoveryClient;

	@Autowired
	private Mapper mapper;

	public List<ServiceModel> loadRouteControllingSerivces() {
		Set<ServiceInstance> services = getServicesByTag("servicetype");

		List<ServiceModel> list = services.stream().map(new Function<ServiceInstance, ServiceModel>() {
			@Override
			public ServiceModel apply(ServiceInstance source) {
				ServiceModel map = mapper.map(source, ServiceModel.class);
				map.setUri(source.getUri());
				return map;
			}
		}).collect(Collectors.toList());

		return list;
	}

	public List<ServiceModel> loadAllRoutes()
	{
		List<ServiceModel> items = loadRouteControllingSerivces();
		for (ServiceModel serviceModel : items) {
			RouteController client = createRouteControllClient(serviceModel.getUri().toString());
			List<String> routesFromClient = client.allExistingRoutes();
			serviceModel.setRoutes(routesFromClient);
		}
		return items;
	}
	
	/**
	 * Find some Services that have the parameter 'tag',and return a Set of their
	 * ServiceIds.
	 * 
	 * @param tag
	 *            Service's tag
	 * @return A set of the services
	 */
	public Set<ServiceInstance> getServicesByTag(String tag) {
		// use Set because some services maybe load balanced
		Set<ServiceInstance> serviceIds = new HashSet<>();
		List<String> services = discoveryClient.getServices();
		for (String string : services) {
			List<ServiceInstance> instantcesFromService = discoveryClient.getInstances(string);
			serviceIds.addAll(instantcesFromService.stream().filter(new Predicate<ServiceInstance>() {
				@Override
				public boolean test(ServiceInstance inst) {
					if (inst.getMetadata().containsKey(tag)) {
						return true;
					}
					return false;
				}
			}).collect(Collectors.toSet()));
		}
		return serviceIds;
	}

	public RouteController getFerginClientFromInstance(String uri, Class serviceClass) {
		Builder builder = createFerginBuilder();
		RouteController fooClient = builder.target(RouteController.class, uri);
		return fooClient;
	}

	private Builder createFerginBuilder() {
		Builder builder = Feign.builder().client(new OkHttpClient()).encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder());
		return builder;
	}

	
	
	public void sendMessageOverService(String serviceId, MessageDTO payload) {

		Builder builder = createFerginBuilder();
		ProduceMessageController fooClient = builder.target(ProduceMessageController.class, serviceId);
		fooClient.sendMessage(payload);

	}

	public void createRoute(List<String> destinationServices, RouteDTO dto) throws Exception {

		for (String destination : destinationServices) {
			RouteController fooClient = createRouteControllClient(destination);
			fooClient.addRoute(dto);
		}

	}

	private RouteController createRouteControllClient(String destination) {
		Builder client = createFerginBuilder();
		RouteController fooClient = client.target(RouteController.class, destination);
		return fooClient;
	}

}
