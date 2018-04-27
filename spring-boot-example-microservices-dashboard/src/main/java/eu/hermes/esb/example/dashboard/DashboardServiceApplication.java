package eu.hermes.esb.example.dashboard;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;
import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@SpringBootApplication
@EnableMicroservicesDashboardServer
@EnableEurekaClient
@EnableAutoConfiguration
public class DashboardServiceApplication {

	public static void main(String[] args) {
		GracefulshutdownSpringApplication.run(DashboardServiceApplication.class, args);
	}

}
