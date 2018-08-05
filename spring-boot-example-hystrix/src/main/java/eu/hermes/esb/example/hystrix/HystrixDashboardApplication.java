package eu.hermes.esb.example.hystrix;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
@EnableHystrixDashboard
public class HystrixDashboardApplication {

  public static void main(String[] args) {
	  GracefulshutdownSpringApplication.run(HystrixDashboardApplication.class, args);
  }

}
