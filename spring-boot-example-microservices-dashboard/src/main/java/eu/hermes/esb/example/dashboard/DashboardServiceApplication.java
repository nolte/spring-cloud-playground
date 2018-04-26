package eu.hermes.esb.example.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import be.ordina.msdashboard.EnableMicroservicesDashboardServer;

@SpringBootApplication
@EnableMicroservicesDashboardServer
@EnableEurekaClient
@EnableAutoConfiguration
public class DashboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardServiceApplication.class, args);
	}

}
