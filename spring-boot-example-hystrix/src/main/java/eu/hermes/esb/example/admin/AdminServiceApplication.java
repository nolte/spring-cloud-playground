package eu.hermes.esb.example.admin;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

import ch.sbb.esta.openshift.gracefullshutdown.GracefulshutdownSpringApplication;

@Configuration
@EnableAutoConfiguration
@EnableEurekaClient
public class AdminServiceApplication {

  public static void main(String[] args) {
	  GracefulshutdownSpringApplication.run(AdminServiceApplication.class, args);
  }

}
