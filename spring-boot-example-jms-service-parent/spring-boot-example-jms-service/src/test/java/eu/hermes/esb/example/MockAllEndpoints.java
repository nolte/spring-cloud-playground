package eu.hermes.esb.example;

import org.apache.camel.impl.InterceptSendToMockEndpointStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockAllEndpoints {

	@Bean
	public InterceptSendToMockEndpointStrategy allEndpointMock() {
		return new InterceptSendToMockEndpointStrategy();
	}
}
