package eu.hermes.esb.example;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.messagehistory.MetricsMessageHistoryFactory;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricRegistry;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for the Camel Log MDC Context
 * 
 * @author FinnernMal
 */
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

  @Autowired
  private MetricRegistry metricRegistry;

  @Bean
  CamelContextConfiguration contextConfiguration() {
    return new CamelContextConfiguration() {
      public void afterApplicationStart(CamelContext arg0) {
        // TODO Auto-generated method stub

      }

      public void beforeApplicationStart(CamelContext context) {
        log.info("Configuring Camel metrics on all routes");
        MetricsRoutePolicyFactory fac = new MetricsRoutePolicyFactory();
        fac.setMetricsRegistry(metricRegistry);
        context.addRoutePolicyFactory(fac);

        MetricsMessageHistoryFactory messageHistoryFactory = new MetricsMessageHistoryFactory();
        messageHistoryFactory.setMetricsRegistry(metricRegistry);
        context.setMessageHistoryFactory(messageHistoryFactory);
      }

    };
  }

}
