package eu.hermes.esb.example.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import eu.hermes.esb.example.frontend.model.HeaderModel;
import eu.hermes.esb.example.frontend.model.MessageModel;
import eu.hermes.esb.example.frontend.model.ProduceRouteModel;
import eu.hermes.esb.example.frontend.model.ServiceModel;
import eu.hermes.esb.example.web.model.MessageDTO;
import eu.hermes.esb.example.web.model.ProducingRouteDTO;

import static org.dozer.loader.api.FieldsMappingOptions.*;
import static org.dozer.loader.api.TypeMappingOptions.*;

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
			mapping(ServiceInstance.class, ServiceModel.class);
			mapping(MessageDTO.class, MessageModel.class);
			mapping(ProducingRouteDTO.class, ProduceRouteModel.class)
					.fields("headers", "additionalHeaders", customConverter(AdditionalHeaderConverter.class))
					.fields("uri", "exit");
			// mapping(ConsumingRouteDTO.class, ConsumingRouteModel.class).fields("uri",
			// "entry");
			// mapping(ProducingRouteDTO.class, ProduceRouteModel.class).fields("uri",
			// "exit").fields("headers",
			// "additionalHeaders");
			// mapping(BridgingRouteDTO.class, BridgeRouteModel.class).fields("uri",
			// "entry").fields("bridgeTarget",
			// "exit");

		}
	};

}
