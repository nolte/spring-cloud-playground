package eu.hermes.esb.example.gateway;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Configuration;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@Configuration
@EnableAutoConfiguration
@EnableZuulProxy
@EnableEurekaClient
public class GatewayServiceApplication {

	public static void main(String[] args) {
		GracefulshutdownSpringApplication.run(GatewayServiceApplication.class, args);
	}

}
