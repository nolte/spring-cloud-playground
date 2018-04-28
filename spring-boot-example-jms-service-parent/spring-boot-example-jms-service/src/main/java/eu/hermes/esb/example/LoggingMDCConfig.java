package eu.hermes.esb.example;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoggingMDCConfig {

	@Value("${camel.useMDCLogging:true}")
	Boolean useMDCLogging;

	private CamelContext camelContext;

	public LoggingMDCConfig(@Autowired CamelContext camelContext) {
		this.camelContext = camelContext;
	}

	@PostConstruct
	public void configureLogMDC() {
		log.debug("Set camel Logging Usage to {}", useMDCLogging);
		camelContext.setUseMDCLogging(useMDCLogging);
	}


}
