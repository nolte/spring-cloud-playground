package eu.hermes.esb.example;

import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.messagehistory.MetricsMessageHistoryFactory;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.codahale.metrics.MetricRegistry;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
@EnableJms
@EnableTransactionManagement
@CamelZipkin
@EnableEurekaClient
@EnableFeignClients(basePackages = { "eu.hermes.esb.example.web.controller" })
public class SpringBootJMSExample {

	@Autowired
	private MetricRegistry metricRegistry;

	public static void main(String[] args) throws Exception {
		GracefulshutdownSpringApplication.run(SpringBootJMSExample.class, args);
	}

	@Bean
	CamelContextConfiguration contextConfiguration() {
		return new CamelContextConfiguration() {
			public void beforeApplicationStart(CamelContext context) {
				log.info("Configuring Camel metrics on all routes");
				MetricsRoutePolicyFactory fac = new MetricsRoutePolicyFactory();
				fac.setMetricsRegistry(metricRegistry);
				context.addRoutePolicyFactory(fac);

				MetricsMessageHistoryFactory messageHistoryFactory = new MetricsMessageHistoryFactory();
				messageHistoryFactory.setMetricsRegistry(metricRegistry);
				context.setMessageHistoryFactory(messageHistoryFactory);
			}

			public void afterApplicationStart(CamelContext arg0) {
				// TODO Auto-generated method stub

			}

		};
	}
}
