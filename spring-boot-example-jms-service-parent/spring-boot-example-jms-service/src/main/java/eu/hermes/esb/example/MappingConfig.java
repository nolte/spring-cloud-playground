package eu.hermes.esb.example;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.hermes.esb.example.model.BridgeRouteModel;
import eu.hermes.esb.example.model.ConsumingRouteModel;
import eu.hermes.esb.example.model.ProduceRouteModel;
import eu.hermes.esb.example.model.RouteModel;
import eu.hermes.esb.example.web.model.BridgingRouteDTO;
import eu.hermes.esb.example.web.model.ConsumingRouteDTO;
import eu.hermes.esb.example.web.model.ProducingRouteDTO;
import eu.hermes.esb.example.web.model.RouteDTO;

@Configuration
public class MappingConfig {

	@Bean
	public DozerBeanMapper mapper() throws Exception {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(objectMappingBuilder);
		return mapper;
	}

	BeanMappingBuilder objectMappingBuilder = new BeanMappingBuilder() {
		@Override
		protected void configure() {
			mapping(RouteDTO.class, RouteModel.class).fields("id", "id");
			mapping(ConsumingRouteDTO.class, ConsumingRouteModel.class).fields("uri", "entry");
			mapping(ProducingRouteDTO.class, ProduceRouteModel.class).fields("uri", "exit").fields("headers",
					"additionalHeaders");
			mapping(BridgingRouteDTO.class, BridgeRouteModel.class).fields("uri", "entry").fields("bridgeTarget",
					"exit");

		}
	};

}
